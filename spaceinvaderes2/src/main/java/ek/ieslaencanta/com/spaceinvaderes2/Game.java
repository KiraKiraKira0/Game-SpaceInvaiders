/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ek.ieslaencanta.com.spaceinvaderes2;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kirae
 */
public class Game {

    private static final int COLUMNS = 80;
    private static final int ROWS = 24;
    private Terminal terminal;
    private Screen screen;
    private boolean exit_key;
    private Ship ship;
    private Bullet bala;

    public Game() {
        this.exit_key = false;
        try {
            this.terminal = new DefaultTerminalFactory().createTerminal();
            this.screen = new TerminalScreen(this.terminal);
            screen.setCursorPosition(null);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        bala = new Bullet(40, 12);
        ship = new Ship(38, 20);
    }

    public void loop() {

        try {
            
            try {
                int x = 0, y = 0;
                screen.startScreen();
                screen.clear();
                while (!this.exit_key) {
                    x = (int) Math.random() * 80;
                    y = (int) Math.random() * 24;
                    process_input();
                    update();
                    paint();
//            System.out.print("ciclo"); // dlya proverci poctavit 1/1
try {
    Thread.sleep((1 / 60) * 1000);
} catch (InterruptedException ex) {
    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
}

//pulsar escape se cierra el terminal y la ventana


                }
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            screen.close();
            terminal.close();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update() {
        this.ship.movebullets();
    }

    private void paint() {
        TerminalSize terminalSize = screen.getTerminalSize();
        //limpiar la pantalla
        for (int column = 0; column < terminalSize.getColumns(); column++) {
            for (int row = 0; row < terminalSize.getRows(); row++) {
                screen.setCharacter(column, row, new TextCharacter(
                        ' ',
                        TextColor.ANSI.DEFAULT,
                        TextColor.ANSI.BLACK));
            }
        }
        this.ship.paint(this.screen);
        try {
            this.bala.paint(this.screen);
            screen.refresh();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void process_input() {
        KeyStroke keyStroke = null;
        try {
            keyStroke = screen.pollInput();

        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        //si se ha pulsado una tecla
        if (keyStroke != null) {
            //si la tecla es la de escape
            if (keyStroke.getKeyType() == KeyType.Escape) {
                this.exit_key = true;
            }
            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                this.bala.moveVertical(-1, 0, Game.ROWS);
                screen.clear();
            }
            if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                this.bala.moveVertical(1, 0, Game.ROWS);
                screen.clear(); // vec ecran
            }
            if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                this.ship.moveHorizontal(-1, 0, Game.COLUMNS);

            }
            if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                this.ship.moveHorizontal(1, 0, Game.COLUMNS);

            }
            if (keyStroke.getKeyType() == KeyType.Enter) {
                this.ship.shoot();

            }
        }
    }

}
