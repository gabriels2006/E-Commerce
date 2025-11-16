import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {

    // Dados Cliente
    private String name;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String telefone;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;

    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor Cliente Básico
    public Client(String name, String birthDate, String email, String password, String telefone) {
        if (!ValidateUtils.validarNome(name)) throw new IllegalArgumentException("Nome inválido!");
        if (!ValidateUtils.validarEmail(email)) throw new IllegalArgumentException("Email inválido!");
        if (!ValidateUtils.validarSenha(password)) throw new IllegalArgumentException("Senha fraca!");
        if (!ValidateUtils.validarTelefone(telefone)) throw new IllegalArgumentException("Telefone inválido!");

        this.name = name;
        this.birthDate = LocalDate.parse(birthDate, DMY);
        this.email = email;
        this.password = password;
        this.telefone = telefone;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    // @Override para quando for printar os dados do Cliente
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Cliente{" +
                "name='" + name + '\'' +
                ", birthDate=" + (birthDate != null ? birthDate.format(formatter) : null) +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cep='" + cep + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}

