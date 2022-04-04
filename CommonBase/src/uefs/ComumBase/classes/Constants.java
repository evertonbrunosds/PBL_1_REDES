package uefs.ComumBase.classes;

public class Constants {

    public static class StatusCode {

        /**
         * Essa resposta provisória indica que tudo ocorreu bem até agora e que
         * o cliente deve continuar com a requisição ou ignorar se já concluiu o
         * que gostaria.
         */
        static final int CONTINUE = 100;

        /**
         * Esse código é enviado em resposta a um cabeçalho de solicitação
         * Upgrade (en-US) pelo cliente, e indica o protocolo a que o servidor
         * está alternando.
         */
        static final int SWITCHING_PROTOCOL = 101;
    }

}
