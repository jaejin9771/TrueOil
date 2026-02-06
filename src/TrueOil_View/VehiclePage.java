package TrueOil_View;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

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

    private JPanel createFuelHistorySection() {
        JPanel card = createBaseCard("⛽ 주유 기록");
        JPanel body = (JPanel) card.getComponent(1);

        JPanel gridContainer = new JPanel(new GridLayout(0, 2, 15, 15));
        gridContainer.setOpaque(false);

        /** [DB POINT] 데이터 로드
         * SELECT * FROM fuel_records ORDER BY log_date DESC LIMIT 6
         */
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

        /** [기능 포인트] AddStationDialog 연동 로직
         * - 버튼 클릭 시 AddStationDialog를 모달(Modal)로 띄움
         * - 다이얼로그에서 저장이 완료되면(isSuccess) UI 새로고침 실행
         */
        addBtn.addActionListener(e -> {
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            // 만약 AddStationDialog 생성자에 Frame이 필요하다면 (Frame)parentWindow 전달
            AddStationDialog dialog = new AddStationDialog((Frame) parentWindow);
            dialog.setVisible(true);

            // 다이얼로그 작업이 끝난 후 성공 여부에 따라 UI 갱신 로직 필요, 근데 이건 addstation과 연결됨
            
        });
        
        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnWrapper.setOpaque(false);
        btnWrapper.add(addBtn);
        body.add(btnWrapper);

        return card;
    }

    /** [기능 포인트] 데이터 로딩 전용 메서드
     * - 초기 실행 시 및 기록 추가 후 UI 갱신을 위해 분리
     */
    private void loadFuelData(JPanel container) {
        container.removeAll();
        // 실제 구현 시 DAO를 통해 DB 데이터를 가져와야 함
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
        
        /** [DB POINT] SELECT current_mileage FROM vehicles */
        JLabel mLabel = new JLabel("<html><font color='gray' size='4'>현재 총 주행거리</font><br><b style='font-size:18pt; color:#1e293b;'>50,000 km</b></html>");
        mileageBox.add(mLabel, BorderLayout.WEST);
        
        body.add(mileageBox);
        body.add(Box.createVerticalStrut(25));

        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 20));
        grid.setOpaque(false);
        
        /** [DB POINT] 소모품 계산 로직 바인딩 */
        grid.add(createHealthItem("엔진 오일", 85, new Color(34, 197, 94)));
        grid.add(createHealthItem("타이어", 65, new Color(234, 179, 8)));
        grid.add(createHealthItem("브레이크 패드", 40, new Color(234, 88, 12)));
        grid.add(createHealthItem("배터리", 90, new Color(34, 197, 94)));

        body.add(grid);
        return card;
    }

    private JPanel createStatsSection() {
        JPanel card = createBaseCard("📊 월별 주유비 통계");
        JPanel body = (JPanel) card.getComponent(1);

        int[] monthlyExpenses = {25, 28, 32, 30, 29, 31}; 
        String[] months = {"1월", "2월", "3월", "4월", "5월", "6월"};

        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(); int h = getHeight() - 50; 
                int leftPadding = 60; int chartW = w - 90; int chartH = h - 40;
                int maxVal = 40; int barSpace = chartW / 6; int barW = barSpace - 35;

                for (int i = 0; i < monthlyExpenses.length; i++) {
                    int x = leftPadding + (i * barSpace) + (barSpace - barW) / 2;
                    int barHeight = (int) ((double) monthlyExpenses[i] / maxVal * chartH);
                    g2.setPaint(new GradientPaint(x, h - barHeight, new Color(96, 165, 250), x, h, new Color(37, 99, 235)));
                    g2.fillRoundRect(x, h - barHeight, barW, barHeight, 10, 10);
                }
            }
        };
        chartPanel.setPreferredSize(new Dimension(0, 280));
        chartPanel.setBackground(Color.WHITE);
        body.add(chartPanel);

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