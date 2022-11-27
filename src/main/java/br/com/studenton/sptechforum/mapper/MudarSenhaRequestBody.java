package br.com.studenton.sptechforum.mapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MudarSenhaRequestBody {

    private String senhaAntiga;

    @Size(min = 8, max = 20, message = "A sua senha deve conter entre 8 a 20 caracteres.")
    @NotBlank
    private String senhaNova;

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }
}
