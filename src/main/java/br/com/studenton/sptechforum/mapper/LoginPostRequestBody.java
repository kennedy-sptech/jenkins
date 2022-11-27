package br.com.studenton.sptechforum.mapper;

public class LoginPostRequestBody {
    private String ra;
    private String senha;

    public LoginPostRequestBody(String ra, String senha) {
        this.ra = ra;
        this.senha = senha;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String pegarSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginPostRequestBody that = (LoginPostRequestBody) o;

        if (ra != null ? !ra.equals(that.ra) : that.ra != null) return false;
        return senha != null ? senha.equals(that.senha) : that.senha == null;
    }

    @Override
    public int hashCode() {
        int result = ra != null ? ra.hashCode() : 0;
        result = 31 * result + (senha != null ? senha.hashCode() : 0);
        return result;
    }
}
