public class Client {

    //Dados Cliente
    private String name;
    private int age;
    private String email;
    private String password;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private String pais;

    //Construtor Cliente Básico

    public Client(String name, int age, String email, String password, String telefone) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.telefone = telefone;
    }

    //Validação do Telefone
    public boolean isTelefoneValido() {
        return telefone != null && telefone.matches("^\\+?\\d{10,15}$");
    }

    //Getters e Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
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
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    //@Override para quando for printar os dados do Cliente ou gerar um novo cliente, ele colocar automaticamente na ordem e com os tipos certos.
    @Override
    public String toString() {
        return "Cliente{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }

}
