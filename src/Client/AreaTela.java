package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Henrique Jardel
 * Aluno do 3° Semestre de Análise e Desenvolvimento de Sistemas
 * 2020
 */

// Classe responsavel pela imagem de fundo e pelas mensagens recebidas.
public class AreaTela extends JTextPane {
    
    private StyledDocument doc;
    private BufferedImage image;
    private ImageIcon icon [];
    private Image emoji;
    private Map <String, ImageIcon> emojis = new HashMap <String , ImageIcon>();
    
    public AreaTela() throws IOException {
        // Variável que pega o documento que esta no JTextPane 
        this.doc = this.getStyledDocument();
        // Troca fonte do texto
        this.setFont(new Font("Calibri",Font.BOLD,15));
        // Coloca uma borda de linha
        this.setBorder(new LineBorder(Color.DARK_GRAY));
        // Faz com que não seja possível editar o texto
        this.setEditable(false);
        // Carrega a imagem de fundo
         this.image = ImageIO.read(new File("C:\\Users\\Henrique\\Documents\\NetBeansProjects\\Chat\\src\\Imagem\\Image4.png"));
         this.icon = new ImageIcon [12];
        // Carrega os emojis
         for (int i = 1; i <= 12;i++)
        {
            this.icon[i-1] = new ImageIcon("C:\\Users\\Henrique\\Documents\\NetBeansProjects\\Chat\\src\\Imagem\\Emojis\\emj" + i + ".png");
        }
        // Cria um HashMap de emojis
        this.emojis.put(":)", this.icon[0]);
        this.emojis.put(":D", this.icon[1]);
        this.emojis.put("'')", this.icon[2]);
        this.emojis.put("8|", this.icon[3]);
        this.emojis.put(":P", this.icon[4]);
        this.emojis.put(":/", this.icon[5]);
        this.emojis.put(":(", this.icon[6]);
        this.emojis.put(":´(", this.icon[7]);
        this.emojis.put(">:(", this.icon[8]);
        this.emojis.put(":O", this.icon[9]);
        this.emojis.put(":o", this.icon[10]);
        this.emojis.put(":|", this.icon[11]);
        }
    
    
    @Override
    public boolean isOpaque()
    {
        return false;
    }
    
    public void append(String msg) throws BadLocationException {
        // Verifica se a mensagem possui algum emoji
        if (!msg.contains("e-")) {
        try {
        // Insere a String sem emojis
        doc.insertString(doc.getLength(), msg, null );
       
    } catch(BadLocationException exc) {
      exc.printStackTrace();
    }
    }
        else {
            // Caso seja somente o emoji
            if (msg.length() == 4) {
            String select = msg.substring("e-".length());
            ImageIcon temp = emojis.get(select);
            this.insertIcon(temp);
            }
            // Mensagem + emoji
            else if (msg.length() > 4) {
                String str [] = msg.split("e-");
                doc.insertString(doc.getLength(),str[0], null);
                setCaretPosition(doc.getLength());
                ImageIcon temp = emojis.get(str[1]);
                if (temp != null) {
                    this.insertIcon(temp);
                }
            }
            // Mensagens digitadas erradas que contem e-
            else {
                 doc.insertString(doc.getLength(), msg, null );
            }
            
        }
    }

    
    // Metodo para pintura do fundo da tela 
    public void paintComponent(Graphics g) {
       Graphics2D g2d = (Graphics2D) g.create();
       g2d.setColor(getBackground());
       g2d.fillRect(0, 0, getWidth(), getHeight());
       if (image != null)
       {
       int x = getWidth() - image.getWidth();
       int y = getHeight() - image.getHeight();
       g2d.drawImage(image,x,y,null);
       }
       super.paintComponent(g2d);
       g2d.dispose();
    }
    
   
}