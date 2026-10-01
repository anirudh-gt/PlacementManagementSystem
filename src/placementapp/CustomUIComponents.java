package placementapp;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomUIComponents {

    public static class RoundedCardPanel extends JPanel {
        private int cornerRadius;
        private Color bgColor;

        public RoundedCardPanel(int radius, Color bg) {
            this.cornerRadius = radius;
            this.bgColor = bg;
            setOpaque(false);
        }

        public void setBgColor(Color color) {
            this.bgColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
            g2.dispose();
        }
    }

    public static class MetricCard extends RoundedCardPanel {
        private String title;
        private String value;
        private Color accentColor;

        public MetricCard(String title, String value, Color accent, Color bg) {
            super(16, bg);
            this.title = title;
            this.value = value;
            this.accentColor = accent;
            setPreferredSize(new Dimension(180, 80));
        }

        public void updateValue(String newValue) {
            this.value = newValue;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Left accent border bar
            g2.setColor(accentColor);
            g2.fillRoundRect(0, 0, 6, getHeight(), 6, 6);

            // Title
            g2.setColor(new Color(156, 163, 175));
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.drawString(title.toUpperCase(), 18, 25);

            // Metric Value
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 22));
            g2.drawString(value, 18, 58);

            g2.dispose();
        }
    }

    public static JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}