package com.github.echological.sc.service.auth.basicauthvalidation;

import com.github.echological.sc.datasource.auth.entity.AuthUserEntity;
import com.github.echological.sc.datasource.auth.repository.AuthUserRepository;
import com.github.echological.sc.global.contract.BusinessServiceContract;
import com.github.echological.sc.global.exception.BusinessServiceValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasicAuthValidationService implements BusinessServiceContract<String, Boolean> {

    private final AuthUserRepository authUserRepository;


    @Override
    public Boolean execute(String input) throws BusinessServiceValidationException {
        // Validasi header Authorization
        if (input == null || input.isBlank()) {
            throw new BusinessServiceValidationException("Authorization header tidak boleh kosong");
        }

        String prefix = "Basic ";
        if (!input.regionMatches(true, 0, prefix, 0, prefix.length())) {
            throw new BusinessServiceValidationException("Skema Authorization tidak valid. Harus menggunakan Basic");
        }

        String base64Credentials = input.substring(prefix.length()).trim();
        if (base64Credentials.isEmpty()) {
            throw new BusinessServiceValidationException("Kredensial Basic tidak ditemukan");
        }

        String userPassDecoded;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            userPassDecoded = new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException ex) {
            throw new BusinessServiceValidationException("Format Base64 pada Authorization tidak valid");
        }

        int sep = userPassDecoded.indexOf(':');
        if (sep < 0) {
            throw new BusinessServiceValidationException("Format kredensial tidak valid. Gunakan username:password");
        }

        String username = userPassDecoded.substring(0, sep);
        String password = userPassDecoded.substring(sep + 1);

        if (username.isBlank() || password.isBlank()) {
            throw new BusinessServiceValidationException("Username atau password tidak boleh kosong");
        }

        // Cari user
        Optional<AuthUserEntity> optionalUser = authUserRepository.findByUsername(username);
        AuthUserEntity user = optionalUser.orElseThrow(
                () -> new BusinessServiceValidationException("User tidak ditemukan")
        );

        // Validasi status akun
        if (!user.isEnabled()) {
            throw new BusinessServiceValidationException("Akun dinonaktifkan");
        }
        if (!user.isAccountNonExpired()) {
            throw new BusinessServiceValidationException("Akun telah kedaluwarsa");
        }
        if (!user.isAccountNonLocked()) {
            throw new BusinessServiceValidationException("Akun terkunci");
        }
        if (!user.isCredentialsNonExpired()) {
            throw new BusinessServiceValidationException("Kredensial telah kedaluwarsa");
        }

        // Verifikasi password
        if (!matchesPassword(password, user.getPassword())) {
            throw new BusinessServiceValidationException("Password tidak sesuai");
        }

        return true;
    }

    private boolean matchesPassword(String raw, String encoded) {
        if (encoded == null || encoded.isBlank()) return false;
        // Dukung format {bcrypt}<hash> atau langsung hash BCrypt ($2a/$2b/$2y)
        String hash = encoded.startsWith("{bcrypt}") ? encoded.substring("{bcrypt}".length()) : encoded;
        return new BCryptPasswordEncoder().matches(raw, hash);
    }



}
