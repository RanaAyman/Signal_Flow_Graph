import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Path2D;

import javax.swing.JPanel;
//
public class Drawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		Path2D.Double path = new Path2D.Double();
		Graphics2D G = (Graphics2D) graphics;
		Font font = new Font("Serif", Font.PLAIN, 24);
		graphics.setFont(font);

		int tempX;

		int horizDisplace = (Data.width - 190) / (Data.nodesNum + 1);
		int vertiCenter = (Data.height - 60) / 2;
		int radius = 25;

		int distanceUp = (int) (vertiCenter - radius);
		int distanceDown = (int) (vertiCenter + radius);
		float selfLoopUpDistance = vertiCenter - 4 * radius;
		float selfLoopDownDistance = vertiCenter + 4 * radius;

		// Draw Input & Output Nodes
		graphics.setColor(Color.pink);
		graphics.fillOval(horizDisplace - radius, vertiCenter - radius, radius * 2, radius * 2);
		graphics.fillOval(horizDisplace * (Data.nodesNum) - radius, vertiCenter - radius, radius * 2, radius * 2);

		graphics.setColor(Color.black);
		graphics.drawString("R(s)", horizDisplace - radius, vertiCenter - 2 * radius);
		graphics.drawString("C(s)", horizDisplace * (Data.nodesNum) - radius, vertiCenter - 2 * radius);

		// Draw Inner Nodes
		graphics.setColor(new Color(135, 206, 235));
		for (int i = 1; i < Data.nodesNum - 1; i++)
			graphics.fillOval(horizDisplace * (i + 1) - radius, vertiCenter - radius, radius * 2, radius * 2);

		graphics.setColor(Color.black);
		for (int i = 0; i < Data.nodesNum; i++)
			graphics.drawString("" + (i + 1), horizDisplace * (i + 1) - radius + 20, vertiCenter + 8);

		// Draw Links
		for (int fromNode = 0; fromNode < Data.nodesNum; fromNode++) {
			for (int toNode = 0; toNode < Data.nodesNum; toNode++) {
				if (Data.gains[fromNode][toNode] != 0) {
					// Self Loops Condition
					if (fromNode == toNode) {
						// Draw Arc
						graphics.setColor(Color.ORANGE);
						path = new Path2D.Double();
						path.moveTo(horizDisplace * (fromNode + 1), distanceUp);
						tempX = horizDisplace * (fromNode + 1) - 3 * radius;
						path.curveTo(tempX, selfLoopUpDistance, tempX, selfLoopDownDistance,
								horizDisplace * (fromNode + 1), distanceDown);
						G.draw(path);

						tempX = tempX + radius - 5;
						// Draw Arrow
						path = new Path2D.Double();
						path.moveTo(tempX + 12, vertiCenter - 10);
						path.lineTo(tempX, vertiCenter + 12);
						path.lineTo(tempX - 12, vertiCenter - 10);
						G.fill(path);
						// Draw Gain Text
						graphics.setColor(Color.black);
						graphics.drawString(Data.gains[fromNode][toNode] + "", horizDisplace * (fromNode + 1) - radius,
								vertiCenter - 2 * radius);
						// Straight Forward Lines Condition
					} else if (toNode - fromNode == 1) {
						// Draw Arc
						graphics.setColor(Color.darkGray);
						graphics.drawLine((fromNode + 1) * horizDisplace + radius, vertiCenter,
								(toNode + 1) * horizDisplace - radius, vertiCenter);
						// Draw Arrow
						tempX = (toNode + fromNode + 2) * horizDisplace / 2;
						path = new Path2D.Double();
						path.moveTo(tempX, vertiCenter - 12);
						path.lineTo(tempX, vertiCenter + 12);
						path.lineTo(tempX + 24, vertiCenter);
						G.fill(path);
						// Draw Gain Text
						graphics.setColor(Color.black);
						graphics.drawString(Data.gains[fromNode][toNode] + "", tempX, vertiCenter - 20);

						// Feedback Condition
					} else if (fromNode > toNode) {
						// Draw Arc
						graphics.setColor(new Color(188, 24, 24));
						path = new Path2D.Double();
						path.moveTo(horizDisplace * (fromNode + 1), distanceDown);
						tempX = horizDisplace * (toNode + fromNode + 2) / 2;
						path.quadTo(tempX, vertiCenter + (fromNode - toNode) * horizDisplace / 2,
								horizDisplace * (toNode + 1), distanceDown);
						G.draw(path);

						// Draw Arrow
						path = new Path2D.Double();
						path.moveTo(tempX - 12, vertiCenter + (fromNode - toNode) * horizDisplace / 4 + 12);
						path.lineTo(tempX + 12, vertiCenter + (fromNode - toNode) * horizDisplace / 4);
						path.lineTo(tempX + 12, vertiCenter + (fromNode - toNode) * horizDisplace / 4 + 24);
						G.fill(path);

						// Draw Gain Text
						graphics.setColor(Color.black);
						graphics.drawString(Data.gains[fromNode][toNode] + "", tempX - 12,
								vertiCenter + (fromNode - toNode) * horizDisplace / 4 - 6);
						// Forward Non-Straight Lines Condition
					} else {
						// Draw Arc
						graphics.setColor(new Color(0,102, 51));
						path = new Path2D.Double();
						path.moveTo(horizDisplace * (fromNode + 1), distanceUp);
						tempX = horizDisplace * (toNode + fromNode + 2) / 2;
						path.quadTo(tempX, vertiCenter - (toNode - fromNode) * horizDisplace / 2,
								horizDisplace * (toNode + 1), distanceUp);
						G.draw(path);

						// Draw Arrow
						path = new Path2D.Double();
						path.moveTo(tempX + 12, vertiCenter - (toNode - fromNode) * horizDisplace / 4 - 12);
						path.lineTo(tempX - 12, vertiCenter - (toNode - fromNode) * horizDisplace / 4);
						path.lineTo(tempX - 12, vertiCenter - (toNode - fromNode) * horizDisplace / 4 - 24);
						G.fill(path);
						// Draw Gain Text
						graphics.setColor(Color.black);
						graphics.drawString(Data.gains[fromNode][toNode] + "", tempX - 12,
								vertiCenter - (toNode - fromNode) * horizDisplace / 4 + 24);
					}
				}
			}

		}
	}

}
