import java.awt.dnd.DropTarget;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class DragDropTestFrame extends JFrame {

	private static final long serialVersionUID = 1L;


	public static void main(String[] args) {

		new DragDropTestFrame();
	}

	public DragDropTestFrame() {
		getContentPane().setBackground(new Color(102, 153, 204));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(495, 295);

		JLabel myLabel = new JLabel("Drag something here!", SwingConstants.CENTER);
		myLabel.setBackground(new Color(153, 204, 255));
		myLabel.setBounds(0, 50, 495, 217);

		MyDragDropListener myDragDropListener = new MyDragDropListener();
		getContentPane().setLayout(null);

		new DropTarget(myLabel, myDragDropListener);

		this.getContentPane().add(myLabel);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 51, 102));
		panel.setBounds(0, 0, 495, 52);
		getContentPane().add(panel);

		this.setVisible(true);
	}
}
