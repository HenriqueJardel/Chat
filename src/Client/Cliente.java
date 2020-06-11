
package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Henrique Jardel
 * Aluno do 3° Semestre de Análise e Desenvolvimento de Sistemas
 * 2020
 */
public class Cliente extends JFrame implements KeyListener, WindowListener {
		
		private Socket socket;
		private AreaTela AreaTela;
		private JTextField AreaEscrita = new JTextField();
                
                private JMenuBar menu = new JMenuBar();
                private JMenu op = new JMenu("Opções");
                private JMenu conexão = new JMenu("Conexões");
                private JMenuItem chatpr = new JMenuItem("Chat privado");
                private JMenuItem chatpu = new JMenuItem("Chat publico");
                private JMenuItem sair = new JMenuItem("Sair");
                private JMenuItem listar = new JMenuItem("Listar");
                
                private String emoji [] = {"<Emojis>", ":)", ":D", "'')" , "8|" , ":P" , ":/" , ":(", ":´(", ">:(", ":O", ":o", ":|"};
                private JComboBox emojis = new JComboBox(emoji);
                
                private PrintStream saida;
		private String id;
                private String ip;
                private String idDestino;
                private int porta;
		
                public Cliente(String id, String ip, int porta) throws IOException, BadLocationException
		{
                        // Variáveis iniciais
                        this.id = id;
                        this.ip = ip;
                        this.porta = porta;

                        // Coloca o nome no Chat
			this.setTitle("Primeiro Chat");
			// Tamanho do Chat
                        this.setSize(400,400);
                        // Incia um novo layout
                        this.setLayout(new BorderLayout());
                        // Tamanho maximo da tela
                        this.setMaximumSize(new Dimension(400,400));
                        // Tamanho minimo da tela
                        this.setMinimumSize(new Dimension(400,400));
                        // Localização inicial = null
                        this.setLocationRelativeTo(null);
			
                        // Insere a AreaTela no Frame
			this.AreaTela = new AreaTela();
                        this.add(AreaTela,BorderLayout.CENTER);
                        this.add(new JScrollPane(AreaTela),BorderLayout.CENTER);
                        
                        // Insere a área de escrita na tela
			this.add(AreaEscrita,BorderLayout.SOUTH);
			this.AreaEscrita.setBackground(Color.LIGHT_GRAY);
			this.AreaEscrita.setPreferredSize(new Dimension(150,25));
			this.AreaEscrita.setBorder(new LineBorder(Color.DARK_GRAY));
                        this.AreaEscrita.setCaretPosition(0);
			this.AreaEscrita.setFont(new Font("Calibri",Font.BOLD,14));
                       
                        // Troca o menu do JFrame 
                        this.setJMenuBar(menu);
                        
                        this.emojis.setPreferredSize(new Dimension(25,20));
                        
                        //Coloca o componente dentro do menu
                        this.menu.add(op);
                        this.menu.add(conexão);
                        this.menu.add(emojis , BorderLayout.NORTH);
                        
                        // Colocando as opções do componente do menu
                        this.op.add(listar);
                        this.op.add(sair);
                        
                        this.conexão.add(chatpr);
                        this.conexão.add(chatpu);
			
                        // Coloca o ouvinte nos itens do menu
                        this.chatpr.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent ae)
                            {
                                idDestino = JOptionPane.showInputDialog("Digite o id de destino");
                                saida.println("set:" + idDestino);
                            }
                        });
                        
                        chatpu.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent ae)
                            {
                                saida.println("set:chat");
                            }
                        });
                        
                        listar.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent ae)
                            {
                                saida.println("List");
                            }
                        });
                        
                        sair.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent ae)
                            {
                                System.exit(0);
                            }
                        });
                        
                        emojis.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent ae)
                            {
                               if(ae.getSource() == emojis)
                               {
                                   JComboBox temp = (JComboBox)ae.getSource();
                                   String emoji = (String) temp.getSelectedItem();
                                   
                                   switch (emoji)
                                   {
                                       case ":)": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case ":D": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case "'')": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case "8|": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case ":P": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case ":/": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                           break;
                                       case ":(": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                           break;
                                       case ":´(": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case ">:(": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case ":O": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case ":o": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                       case ":|": AreaEscrita.setText(AreaEscrita.getText() + " e-" + emoji);
                                            emojis.setSelectedIndex(0);
                                            break;
                                   }
                               }
                            }
                        });
                        
                        
                        
                        
                        // Aguarda o usuário decidir se vai fechar a janela
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			// Deixa a janela visível
                        this.setVisible(true);
			
			// Coloca o ouvinte dentro do JTextfield
			this.AreaEscrita.addKeyListener(this);
                            
                        // Coloca o ouvinte da janela para detectar a saída
                        this.addWindowListener(this);
                        
                        this.pack();
			
                        // Inicia a conexão com o server
			init();
			
			
		}
                
                
                
		
	
	
		
	    public void init() throws IOException, BadLocationException {
	    	// Tenta se conectar ao server
                try {
                this.socket = new Socket(this.ip,porta);
                } catch (Exception erro) // Caso não consiga ele manda uma mensagem alertando o usuario
                {
                    JOptionPane.showMessageDialog(null,"Servidor não encontrado!\nVerifique se a porta e o ip estão corretos");
                    System.exit(0);
                }
                // inicia a variavel de saida 
                this.saida = new PrintStream(this.socket.getOutputStream());
	    	// Manda o id do usuario e o destino 
                this.saida.println("meuid:"+this.id + " idDestino:"+ this.idDestino);
	    	
                // Mensagens inicias do chat
	    	this.AreaTela.append("Bem vindo " + id + "!\n");
                this.AreaTela.append("Use o menu superior para conectar ou troca conexão\n");
                this.AreaTela.append("Use o menu superior para listar usuarios\n");
	    	this.AreaTela.setCaretPosition(AreaTela.getDocument().getLength());
                
	    	// Thread para escutar o servidor 
	    	new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    escutar();
	                } catch (IOException e) {
	                System.out.println("Erro ao escutar o server!");
	                } catch (BadLocationException ex) {
                            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
	            }
	        }).start();
	    	
	    	
	        
	    }
            // Método para jogar na tela as informações recebidas
	    public void escutar() throws IOException, BadLocationException {
	        Scanner entrada = new Scanner(this.socket.getInputStream());
	        while(entrada.hasNextLine()){
	            String mensagem = entrada.nextLine();
                    if (!mensagem.trim().isEmpty())
                    {
	            	this.AreaTela.append(mensagem);
                        this.AreaTela.append("\n");
                        this.AreaTela.setCaretPosition(AreaTela.getDocument().getLength());
                    }
	           
	        }
	    }
            
            
            public static void main (String [] args) throws IOException, BadLocationException
            {
                // Janela inicial
                
                JTextField nome = new JTextField();
                JTextField ip = new JTextField();
                JTextField porta = new JTextField();
                
                ip.setText("127.0.0.1");
                porta.setText("9999");
                
                Object [] conteiner = {"Nome",nome,"Ip Servidor",ip,"Porta",porta};
                JOptionPane.showConfirmDialog(null,conteiner,"Inciando a conexão", JOptionPane.OK_CANCEL_OPTION,2);
                
                if (nome.getText().length() < 1) {
                    JOptionPane.showMessageDialog(null,"Nome invalido!");
                }
                
                else if (porta.getText().length() < 1) {
                    JOptionPane.showMessageDialog(null,"Porta invalida!");
                }
                
                else {
                int p  = Integer.parseInt(porta.getText());
                // Inicia o chat
                new Cliente(nome.getText(),ip.getText(),p);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // Implementação não utilizada
            }
            
            
            // Método de envio de mensagens
            @Override
            public void keyPressed(KeyEvent e) {
                    
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        // Joga a mensagem na tela
                        //AreaTela.setCaretPosition(AreaTela.getDocument().getLength());
                        AreaTela.append("Você disse: " + AreaEscrita.getText());
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        AreaTela.append("\n");
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    AreaTela.setCaretPosition(AreaTela.getDocument().getLength());
                    String mensagem = "";
                    mensagem += AreaEscrita.getText();
                        if (!mensagem.trim().isEmpty()) {
                            //Envia a mensagem para o servidor
                            saida.println(mensagem);
                        }
                        
                    // Apaga o texto da area escrita
                    AreaEscrita.setText("");
                    // Pede o foco da area escrita
                    AreaEscrita.requestFocus();
                    // Posiciona o cursor do teclado no início do texto
                    AreaEscrita.setCaretPosition(0);
                        
                }
            }
            
            
            
            @Override
            public void keyReleased(KeyEvent e) {
                // Implementação não utilizada
            }

            @Override
            public void windowOpened(WindowEvent we) {
            }
            // Ouvinte responsável pelo evento de fechamento da janela
            @Override
            public void windowClosing(WindowEvent we) {
            int s = JOptionPane.showConfirmDialog(null,"Deseja sair?");
                if (s == JOptionPane.YES_OPTION) {
                // Manda a mensagem de logoff para o servidor
                saida.println("close:" + this.id);
                System.exit(0);
            }
            }

            @Override
            public void windowClosed(WindowEvent we) {
                // Implementação não utilizada
            }

            @Override
            public void windowIconified(WindowEvent we) {
                // Implementação não utilizada
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
                // Implementação não utilizada
            }

            @Override
            public void windowActivated(WindowEvent we) {
                // Implementação não utilizada
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
                // Implementação não utilizada
            }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    
}