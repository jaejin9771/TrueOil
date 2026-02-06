package TrueOil_View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class VehiclePage extends JScrollPane {

    public VehiclePage() {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getVerticalScrollBar().setUnitIncrement(20);
        setBorder(null);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(243, 244, 246));
        container.setBorder(new EmptyBorder(40, 80, 40, 80));

        JLabel title = new JLabel("차량 관리 / 차계부");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.add(title);
        container.add(Box.createVerticalStrut(30));

        container.add(createHealthSection());
        container.add(Box.createVerticalStrut(25));
        container.add(createFuelHistorySection());
        container.add(Box.createVerticalStrut(25));
        container.add(createStatsSection());

        container.add(Box.createVerticalStrut(60));
        setViewportView(container);
    }

    private JPanel createStatsSection() {
        JPanel card = createBaseCard("📊 월별 주유비 통계");
        JPanel body = (JPanel) card.getComponent(1);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(250000, "주유비", "1월");
        dataset.addValue(280000, "주유비", "2월");
        dataset.addValue(320000, "주유비", "3월");
        dataset.addValue(300000, "주유비", "4월");
        dataset.addValue(290000, "주유비", "5월");
        dataset.addValue(310000, "주유비", "6월");

        JFreeChart chart = ChartFactory.createBarChart(
                null, 
                null, 
                null, 
                dataset,
                PlotOrientation.VERTICAL,
                false, 
                true, 
                false
        );

        customizeChart(chart);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(0, 300));
        chartPanel.setBackground(Color.WHITE);
        
        body.add(chartPanel);

        return card;
    }

    private void customizeChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(229, 231, 235));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(37, 99, 235));
        renderer.setItemMargin(0.5);
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        domainAxis.setAxisLineVisible(false);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        rangeAxis.setAxisLineVisible(false);
    }

    private JPanel createFuelHistorySection() {
        JPanel card = createBaseCard("⛽ 주유 기록");
        JPanel body = (JPanel) card.getComponent(1);

        JPanel gridContainer = new JPanel(new GridLayout(0, 2, 15, 15));
        gridContainer.setOpaque(false);

        loadFuelData(gridContainer);

        body.add(gridContainer);
        body.add(Box.createVerticalStrut(25));

        JButton addBtn = new JButton("+ 새로운 주유 기록 등록");
        addBtn.setPreferredSize(new Dimension(280, 50));
        addBtn.setBackground(new Color(37, 99, 235));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addBtn.addActionListener(e -> {
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            AddStationDialog dialog = new AddStationDialog((Frame) parentWindow);
            dialog.setVisible(true);
        });

        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnWrapper.setOpaque(false);
        btnWrapper.add(addBtn);
        body.add(btnWrapper);

        return card;
    }

    private void loadFuelData(JPanel container) {
        container.removeAll();
        String[][] history = {
                {"2026-01-25", "주유소 A", "45,000원", "30L"},
                {"2026-01-18", "주유소 B", "40,000원", "26L"},
                {"2026-01-12", "주유소 C", "50,000원", "32L"},
                {"2026-01-05", "주유소 D", "38,000원", "24L"}
        };

        for (String[] h : history) {
            container.add(createFuelItem(h[0], h[1], h[2], h[3]));
        }
    }

    private JPanel createFuelItem(String date, String station, String price, String liter) {
        JPanel item = new JPanel(new BorderLayout(10, 0));
        item.setBackground(Color.WHITE);
        item.setBorder(new CompoundBorder(
                new LineBorder(new Color(241, 245, 249)),
                new EmptyBorder(15, 18, 15, 18)
        ));

        JPanel left = new JPanel(new GridLayout(2, 1, 0, 3));
        left.setOpaque(false);
        JLabel dateLbl = new JLabel(date);
        dateLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        dateLbl.setForeground(Color.GRAY);
        JLabel stationLbl = new JLabel(station);
        stationLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        left.add(dateLbl); left.add(stationLbl);

        JPanel right = new JPanel(new GridLayout(2, 1, 0, 3));
        right.setOpaque(false);
        JLabel priceLbl = new JLabel(price, SwingConstants.RIGHT);
        priceLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        priceLbl.setForeground(new Color(37, 99, 235));
        JLabel literLbl = new JLabel(liter, SwingConstants.RIGHT);
        literLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        literLbl.setForeground(Color.GRAY);
        right.add(priceLbl); right.add(literLbl);

        item.add(left, BorderLayout.CENTER);
        item.add(right, BorderLayout.EAST);

        return item;
    }

    private JPanel createHealthSection() {
        JPanel card = createBaseCard("🔧 소모품 건강도");
        JPanel body = (JPanel) card.getComponent(1);

        JPanel mileageBox = new JPanel(new BorderLayout());
        mileageBox.setBackground(new Color(249, 250, 251));
        mileageBox.setBorder(new CompoundBorder(new LineBorder(new Color(229, 231, 235)), new EmptyBorder(20, 25, 20, 25)));
        mileageBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel mLabel = new JLabel("<html><font color='gray' size='4'>현재 총 주행거리</font><br><b style='font-size:18pt; color:#1e293b;'>50,000 km</b></html>");
        mileageBox.add(mLabel, BorderLayout.WEST);

        body.add(mileageBox);
        body.add(Box.createVerticalStrut(25));

        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 20));
        grid.setOpaque(false);

        Object[][] healthItems = {
                {"엔진 오일", 50, new Color(34, 197, 94)},
                {"타이어", 65, new Color(234, 179, 8)},
                {"브레이크 패드", 40, new Color(234, 88, 12)},
                {"배터리", 90, new Color(34, 197, 94)}
        };

        for (Object[] item : healthItems) {
            String name = (String) item[0];
            int value = (int) item[1];
            Color color = (Color) item[2];
            grid.add(createHealthItem(name, value, color));
        }

        body.add(grid);
        return card;
    }

    private JPanel createHealthItem(String name, int value, Color color) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(new Color(235, 237, 240)), new EmptyBorder(18, 20, 18, 20)));
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(new JLabel(name), BorderLayout.WEST);
        header.add(new JLabel(value + "%"), BorderLayout.EAST);
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(value);
        bar.setForeground(color);
        p.add(header); p.add(Box.createVerticalStrut(12)); p.add(bar);
        return p;
    }

    private JPanel createBaseCard(String titleText) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(new Color(225, 228, 232), 1), new EmptyBorder(30, 35, 30, 35)));
        JLabel t = new JLabel(titleText);
        t.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
        t.setBorder(new EmptyBorder(0, 0, 20, 0));
        p.add(t, BorderLayout.NORTH);
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        p.add(body, BorderLayout.CENTER);
        return p;
    }
}