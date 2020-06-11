package Server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Henrique Jardel
 * Aluno do 3° Semestre de Análise e Desenvolvimento de Sistemas
 * 2020
 */
public class Gerenciador extends Thread {
	
            private String id;
            private String idDestino;
            private int tipo;
	    private Socket socket;
	    private static Map<String , Gerenciador> logados = new HashMap<String , Gerenciador>();
	    private static Map<String,String> Conexoes = new HashMap<String,String>();

	    public void init(){
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    escutar();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }).start();
	    }

	    public Gerenciador (Socket socket){
	        this.socket = socket;
                this.idDestino = "";
                this.tipo = 1;
	        init();
	    }

	    // Método de envio para chat privado
            public void enviarMensagem(String conectado, String mensagem) throws IOException {
                // Verifica se existe conexão
                    if (!conectado.trim().isEmpty()) {
                    /* Verifica se existe mensagem
                     Primeiro e retirado os espaços através da função trim()
                     Verifica se esta vazia*/
                    if (!mensagem.trim().isEmpty()) {
                    // Pega o cliente
                    Gerenciador cliente = logados.get(conectado);
                    // Verifica se o cliente existe
                    if (cliente != null) {
                        // Formata a mensagem 
                        String msgfinal = "";
                        msgfinal = this.id + " disse: " + mensagem;
                        cliente.escrever(msgfinal);
                    }
                    else
                        this.escrever("Não existe cliente conectado!");
                        
                    
                    }
                   else
                    this.escrever("Mensagem invalida!");
                }
               else
               {
                   this.escrever("Não existe o conexão!");
               }
	    }
            
            // Método de envio chat publico
            public void publicChat(String mensagem) throws IOException
            {
               Gerenciador cliente;
               for (String c : logados.keySet())
               {
                    if (c != this.id)
                    {   
                        cliente = logados.get(c);
                        if (cliente.getTipo() == 2)
                        {
                            String msgfinal = "";
                            msgfinal = this.id + " disse: " + mensagem;
                            cliente.escrever(msgfinal);
                        }
                    }
               }
            }
            
	    //Método de saida
            public void escrever(String mensagem) throws IOException {
                PrintStream saida = new PrintStream(this.socket.getOutputStream());
	        saida.println(mensagem);
	    }

	    
	    // Método de escuta do servidor
	    public void escutar() throws IOException {
                
                //Objeto da classe Scanner 
                 //Serve para ler as saídas do cliente através do InputStream 
	        Scanner entrada = new Scanner(this.socket.getInputStream());
	     
	        while(entrada.hasNextLine()){

	            String texto = entrada.nextLine();
	            // A primeira mensagem vem somente com id
                    // O idDestino incia como null
	            
                    if(texto.contains("meuid:")){
                        String [] mensagem = texto.split(" ");
                        // Pega somente o necessário das duas mensagens
                        this.id = mensagem[0].substring("meuid:".length());
                        this.idDestino = mensagem[1].substring("idDestino:".length());
                        
                        // Coloca as variáveis id e Gerenciador no HashMap
                        this.logados.put(this.id,this);
	                // Lista de conexões do cliente 
                        // Colocando o nome da Thread do cliente e o destino no HashMap
                        this.Conexoes.put(this.getName(),this.idDestino);
                        System.out.println("Um novo cliente entrou no sever!");
	                continue;
	            }
                    // Troca à conexão do cliente 
                    else if (texto.contains("set")) {
                        this.idDestino = texto.substring("set:".length());
                        this.Conexoes.replace(this.getName(),this.idDestino);
                        
                        if (!idDestino.equals("chat"))
                        {
                            Gerenciador cliente = this.logados.get(idDestino);
                            if (cliente == null)
                            {
                                this.escrever("No momento o cliente não esta conectado");
                                this.escrever("Tente novamente mais tarde!");
                                this.tipo = 1;
                            }
                            else
                            {
                                this.escrever("Conexão realizada!");
                                this.tipo = 1;
                            }
                        }
                        else
                        {
                            this.escrever("Conectado ao chat público");
                            this.tipo = 2;
                        }
                    }
                    // Lista usuários ativos no servidor
                    else if(texto.contains("Lis")) {
                        StringBuffer lista = new StringBuffer();
                        this.escrever("Usuários Logados no Momento");
                        for (String c : logados.keySet())
                        {
                            this.escrever(c);
                        }
                    }
                    // Tira o cliente da lista assim que ele fecha o chat
                    else if (texto.contains("close:")) {
                       String saindo = texto.substring("close:".length());
                       this.logados.remove(saindo);
                       this.Conexoes.remove(this.getName());
                    }
                    // Manda a mensagem 
                    else {
                        // Pega o destino da mensagem 
                        String conectado = Conexoes.get(this.getName());
                        // Envia a mensagem
                        if (conectado.equals("chat"))
                            publicChat(texto);
                        else
                            enviarMensagem(conectado,texto);
                    }
                }
	    }

    public int getTipo() {
        return tipo;
    }
            
}