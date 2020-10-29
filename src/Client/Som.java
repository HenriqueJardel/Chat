/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author henri
 */
public class Som {
    
    private Player player;
    private InputStream file;
    private BufferedInputStream song;
    
    private final String path = "Som/Song.mp3";    
    
    public void Play(String path) {
        try {
            this.file = getClass().getClassLoader().getResourceAsStream(path);
            this.song = new BufferedInputStream(file);
            this.player = new Player(song);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {}
            }
        }).start();
    }
    
    public String getPath()
    {
        return path;
    }
}
