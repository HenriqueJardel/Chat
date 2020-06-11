package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Henrique Jardel
 * Aluno do 3° Semestre de Análise e Desenvolvimento de Sistemas
 * 2020
 */

public class Server {
   public static void main (String [] args) 
   {
        ServerSocket servidor = null;
       try {  
           // Inicia um servidor com a porta 9999
           System.out.println("Inciando servidor!!");
           servidor = new ServerSocket(9999);
           System.out.println("Server Inciado com sucesso!");
          
           while (true)
           {
               Socket socket = servidor.accept();
               // Gera uma nova Thread para cada cliente conectado
               new Gerenciador(socket);
           }
                         
               
       } catch (IOException ex) {
            System.out.println("Porta indisponivel!");
            try {
                if (servidor != null)
                servidor.close();
                } catch (IOException ex1) {
                System.err.println("Porta indisponivel ou server fechado!");
            }
       }
   }
}
