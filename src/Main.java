public class Main {
    public static void main(String[] args) {

        Client C1 = new Client("Gabriel", 19, "gabriel@123.com","SuaSenha@123","(11) 98792-4054");

        // Validação de telefone
        if (ValidateUtils.ValidadorUtils.validarTelefone(C1.getTelefone())) {
            System.out.println("Telefone válido");
        } else {
            System.out.println("Telefone inválido");
        }
        // Validação de email
        if (ValidateUtils.ValidadorUtils.validarEmail(C1.getEmail())) {
            System.out.println("Email válido!");
        } else {
            System.out.println("Email inválido!");
        }
        // Validação de Senha
        if (!ValidateUtils.ValidadorUtils.validarSenha(C1.getPassword())) {
            System.out.println("Senha fraca!");
        } else {
            System.out.println("Senha forte!");
        }
    }
}

