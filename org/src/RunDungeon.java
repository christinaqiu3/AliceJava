import java.awt.*;
import javax.swing.*;

public class RunDungeon implements Runnable {
    public void run() {

        Font f1 = new Font("Serif", Font.BOLD, 20);

        Color lp = new Color(160, 119, 202);
        Color dp = new Color(121, 65, 164);
        Color ddp = new Color(61, 18, 93);

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Alice in Dungeon Land");
        frame.setLocation(300, 300);
        frame.setBackground(dp);
        frame.setFont(f1);
        frame.setForeground(lp);

        // Status panel
        final JPanel status_panel = new JPanel();
        final JPanel status_panel2 = new JPanel();

        BoxLayout bl = new BoxLayout(status_panel2, BoxLayout.Y_AXIS);
        status_panel2.setLayout(bl);

        frame.add(status_panel2, BorderLayout.SOUTH);

        status_panel.setLayout(new GridBagLayout());

        final JLabel status2 = new JLabel("Kills: ");
        status2.setForeground(lp);
        status2.setFont(f1);
        status_panel.add(status2);

        final JLabel status4 = new JLabel("0"); // kills
        status4.setForeground(lp);
        status4.setFont(f1);
        status_panel.add(status4);

        final JLabel statusL = new JLabel("   |   LVL: ");
        statusL.setForeground(lp);
        statusL.setFont(f1);
        status_panel.add(statusL);

        final JLabel status5 = new JLabel("1");
        status5.setForeground(lp);
        status5.setFont(f1);
        status_panel.add(status5);

        final JLabel statusH = new JLabel("   |   Health: ");
        statusH.setForeground(lp);
        statusH.setFont(f1);
        status_panel.add(statusH);

        final JLabel status6 = new JLabel("30");
        status6.setForeground(lp);
        status6.setFont(f1);
        status_panel.add(status6);

        final JLabel status = new JLabel("Running...");
        status.setForeground(lp);
        status.setFont(f1);

        final JPanel status_panel3 = new JPanel();
        status_panel3.add(status);

        status_panel2.add(status_panel);
        status_panel2.add(status_panel3);

        final JLabel statusS = new JLabel("Started");
        statusS.setForeground(lp);
        statusS.setFont(f1);
        final JPanel status_panelS = new JPanel();

        status_panelS.add(statusS);
        status_panelS.setBackground(ddp);

        status_panel2.add(status_panelS);

        status_panel.setBackground(ddp);
        status_panel3.setBackground(ddp);

        ///////// Instructions ///////////
        final JFrame infoFrame = new JFrame("Instructions");
        infoFrame.setBackground(dp);
        infoFrame.setLocation(820, 315);

        final JPanel i = new JPanel();
        i.setForeground(lp);
        i.setFont(f1);
        i.setBackground(ddp);
        infoFrame.add(i);

        // Game board
        final DungeonBoard dungeonBoard = new DungeonBoard(
                status, status4, status5, status6, statusS
        );
        frame.add(dungeonBoard, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JPanel ll = new JPanel();
        ll.setBackground(ddp);
        frame.add(ll, BorderLayout.EAST);

        final JPanel ll2 = new JPanel();
        ll2.setBackground(ddp);
        frame.add(ll2, BorderLayout.WEST);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> dungeonBoard.reset());
        control_panel.add(reset);

        final JButton quick_save = new JButton("Quick Save");
        quick_save.addActionListener(e -> dungeonBoard.quickSave());// change to save
        control_panel.add(quick_save);

        final JButton load_save = new JButton("Load Save");
        load_save.addActionListener(e -> dungeonBoard.loadSave());// change to load save
        control_panel.add(load_save);

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> infoFrame.setVisible(true));
        control_panel.add(instructions);
        control_panel.setBackground(ddp);

        final JLabel infoStatus = new JLabel(
                """
                        <html>You, Alice, are the white circle.
                        <br/>Your goal is to kill as many enemies you can >:)
                        <br/>(without getting killed yourself)
                        <br/>
                        <br/>Your enemies are the black circles.
                        <br/>Each has a specific attack they will be damaged by.
                        <br/>Do the right attack (A or B or C) to kill them >:D
                        <br/>Beware, they will fight back until they are dead!
                        <br/>
                        <br/>You can find treasures in the gold boxes that have:
                        <br/> * Stronger weapons
                        <br/> * Health potions (press H to use)
                        <br/> * Nothing
                        <br/> * A trap
                        <br/>
                        <br/>Go to the next level by falling through the hole on
                        <br/>the other side of the room! Move using arrow keys.
                        <br/>
                        <br/>Beat the Dungeon by making it to the end!
                        </html>"""
        );
        infoStatus.setForeground(lp);
        infoStatus.setFont(f1);
        infoStatus.setHorizontalAlignment(SwingConstants.CENTER);
        infoStatus.setBackground(ddp);
        infoFrame.add(infoStatus);
        infoFrame.setBackground(ddp);
        infoFrame.setForeground(ddp);
        infoFrame.setOpacity(1);
        infoStatus.setOpaque(false);

        // Put the frame on the screen
        frame.pack();
        frame.setForeground(ddp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        infoFrame.pack();
        infoFrame.setVisible(true);
        infoFrame.setBackground(ddp);
        infoFrame.setSize(450, 500);

        // Start the game
        dungeonBoard.reset();
    }

}
