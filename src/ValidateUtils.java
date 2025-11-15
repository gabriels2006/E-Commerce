public class ValidateUtils {
    public class ValidadorUtils {

        // Validação de telefone: aceita +55, números com 10 a 15 dígitos
        public static boolean validarTelefone(String telefone) {
            if (telefone == null) return false;

            // Remove tudo que não for número
            String telefoneLimpo = telefone.replaceAll("[^\\d]", "");

            // Verifica se tem entre 10 e 15 dígitos
            return telefoneLimpo.matches("^\\d{10,15}$");
        }

        // Validação de e-mail simples
        public static boolean validarEmail(String email) {
            return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        }

        // (?=.*[A-Za-z]) → pelo menos uma letra
        //
        //(?=.*\\d) → pelo menos um número
        //
        //(?=.*[@$!%*?&]) → pelo menos um símbolo
        //
        //[A-Za-z\\d@$!%*?&]{8,} → mínimo 8 caracteres, contendo letras, números e símbolos permitidos

        public static boolean validarSenha(String senha) {
            return senha != null && senha.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        }

        // Validação de nome: apenas letras e espaços
        public static boolean validarNome(String nome) {
            return nome != null && nome.matches("^[A-Za-zÀ-ÿ\\s]{2,}$");
        }
    }

}
