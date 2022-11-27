package br.com.studenton.sptechforum.mapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EsqueciSenhaBody {

    private String ra;

    private String email;

    @Size(min = 8, max = 20, message = "A sua senha deve conter entre 8 a 20 caracteres.")
    @NotBlank
    private String senhaNova;

    public String getRa() {
        return ra;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaNova() {
        return senhaNova;
    }
}
