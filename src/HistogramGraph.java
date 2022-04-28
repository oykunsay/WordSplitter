import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HistogramGraph {
	
	DatabaseOperations dbOper = new DatabaseOperations();

	public HistogramGraph() throws SQLException {

		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		dbOper.getWordCount(arr);

		JFrame frame = new JFrame("Word-Count Histogram Graph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(new JScrollPane(new Graph(arr)));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected class Graph extends JPanel {

		protected static final int MIN_BAR_WIDTH = 4;
		private ArrayList<Integer> arr;

		public Graph(ArrayList<Integer> arr) {
			this.arr = arr;
			int width = (arr.size() * MIN_BAR_WIDTH) + 11;
			Dimension minSize = new Dimension(width, 128);
			Dimension prefSize = new Dimension(width, 256);
			setMinimumSize(minSize);
			setPreferredSize(prefSize);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (arr != null) {
				int xOffset = 5;
				int yOffset = 5;
				int width = getWidth() - 1 - (xOffset * 2);
				int height = getHeight() - 1 - (yOffset * 2);
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setColor(Color.DARK_GRAY);
				g2d.drawRect(xOffset, yOffset, width, height);
				int barWidth = Math.max(MIN_BAR_WIDTH, (int) Math.floor((float) width / (float) arr.size()));

				int maxValue = Collections.max(arr);
				int xPos = xOffset;
				for (int i = 0; i < arr.size(); i++) {
					int value = arr.get(i);
					int barHeight = Math.round(((float) value / (float) maxValue) * height);
					g2d.setColor(new Color(i, i, i));
					int yPos = height + yOffset - barHeight;
					Rectangle2D bar = new Rectangle2D.Float(xPos, yPos, barWidth, barHeight);
					g2d.fill(bar);
					g2d.setColor(Color.DARK_GRAY);
					g2d.draw(bar);
					xPos += barWidth;
				}
				g2d.dispose();
			}
		}
	}
}