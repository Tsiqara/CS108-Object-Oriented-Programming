import javax.swing.*;
import java.awt.*;

public class JBrainTetris extends JTetris{
    private Brain brain;
    private Brain.Move move;
//    private Brain.Move adversaryMove;
    private JCheckBox brainMode;
    private JCheckBox animateFall;
    private JSlider adversary;
    private JLabel adversaryStatus;

    private int countChanged; // with this we control if JTetris count changed

    JBrainTetris(int pixels) {
        super(pixels);
        brain = new DefaultBrain();
        countChanged = 0;
    }

    @Override
    public JComponent createControlPanel(){
        JComponent panel = super.createControlPanel();

        panel.add(Box.createVerticalStrut(15));
        panel.add(new JLabel("Brain:"));
        brainMode = new JCheckBox("Brain active");
        animateFall = new JCheckBox("Animate Falling");
        panel.add(brainMode);
        panel.add(animateFall);
        animateFall.setSelected(true);

        panel.add(Box.createVerticalStrut(12));
        JPanel little = new JPanel();
        little.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0); // min, max, current
        adversary.setPreferredSize(new Dimension(100,15));
        little.add(adversary);
        adversaryStatus = new JLabel("ok");
        little.add(adversaryStatus);
        panel.add(little);

        panel.add(Box.createVerticalStrut(15));
        return panel;
    }

    @Override
    public void tick(int verb){
        if(verb == DOWN && brainMode.isSelected()){
            if(countChanged != super.count){ // piece added
                countChanged = count;
                board.undo();
                move = brain.bestMove(board, currentPiece, board.getHeight() - TOP_SPACE, move);
            }
            if(move != null){
                if(!move.piece.equals(currentPiece)){
                    super.tick(ROTATE);
                }
                if(move.x < currentX){
                    super.tick(LEFT);
                }else if(move.x > currentX){
                    super.tick(RIGHT);
                }else if(!animateFall.isSelected() && move.x == currentX && move.y > currentY){
                    super.tick(DROP);
                }
            }
        }
        super.tick(verb);
    }

    @Override
    public Piece pickNextPiece(){
        int ad = adversary.getValue();
        int rnd = random.nextInt(99);
        if(ad == 0 || rnd >= ad){
            adversaryStatus.setText("ok");
            return super.pickNextPiece();
        }
        
        adversaryStatus.setText("*ok*");
        double worstScore = 0;
        Brain.Move curr = null;
        Piece nextPiece = super.pickNextPiece();
        for(int i = 0; i < pieces.length; i ++){
           curr =  brain.bestMove(board, pieces[i], board.getHeight() - TOP_SPACE, curr);
           if(curr != null && curr.score > worstScore){
               nextPiece = curr.piece;
               worstScore = curr.score;
           }
        }
        
        return nextPiece;
    }

    /**
     Creates a frame with a JBrainTetris.
     */
    public static void main(String[] args) {
        // Set GUI Look And Feel Boilerplate.
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JTetris tetris = new JBrainTetris(16);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setVisible(true);
    }
}
