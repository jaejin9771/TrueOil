package TrueOil_View;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class StationDetailPage extends JScrollPane {

    public StationDetailPage(String stationName) {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setBorder(null);
        getVerticalScrollBar().setUnitIncrement(20);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(243, 244, 246));
        container.setBorder(new EmptyBorder(40, 100, 40, 100)); 

        container.add(createHeader(stationName));
        container.add(Box.createVerticalStrut(30));
        container.add(createBasicInfoCard(stationName));
        container.add(Box.createVerticalStrut(25));
        container.add(createPriceInfoCard());
        container.add(Box.createVerticalStrut(25));
        container.add(createDistanceCostCard());
        container.add(Box.createVerticalStrut(25));
        container.add(createMapCard(stationName)); // 액션 추가된 섹션
        container.add(Box.createVerticalStrut(60));

        setViewportView(container);
    }

    private JPanel createMapCard(String name) {
        JPanel card = createBaseCard("지도");
        
        JPanel mapArea = new JPanel(new GridBagLayout());
        mapArea.setPreferredSize(new Dimension(0, 300));
        mapArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        mapArea.setBackground(new Color(230, 233, 237));
        mapArea.setBorder(new LineBorder(new Color(210, 214, 219)));
        mapArea.add(new JLabel("네이버 지도 API 연동 영역"));

        JPanel btnGrid = new JPanel(new GridLayout(1, 2, 15, 0));
        btnGrid.setOpaque(false);
        btnGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JButton naviBtn = createStyledButton("네이버 지도에서 보기", new Color(0, 199, 60));
        JButton routeBtn = createStyledButton("길찾기", new Color(37, 99, 235));

     // [ACTION POINT] 네이버 지도 웹 상세 페이지 연결
        naviBtn.addActionListener(e -> {
            /** * 1. 주유소 명칭(name) 또는 좌표 데이터를 기반으로 URL 생성
             * 2. java.awt.Desktop 클래스를 이용해 외부 브라우저 호출 로직 구현 예정
             */
        });

        // [ACTION POINT] 네이버 지도 길찾기 서비스 연결
        routeBtn.addActionListener(e -> {
            /** * 1. 현재 사용자 좌표와 목적지(주유소) 좌표 파라미터 구성
             * 2. 네이버/카카오 맵 길찾기 스키마 혹은 웹 URL 호출 로직 구현 예정
             */
        });

        btnGrid.add(naviBtn); btnGrid.add(routeBtn);
        
        card.add(mapArea);
        card.add(Box.createVerticalStrut(20));
        card.add(btnGrid);
        return card;
    }

    private JPanel createHeader(String name) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel title = new JLabel("주유소 상세 정보 (" + name + ")"); 
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        
        JButton backBtn = new JButton("← 뒤로가기");
        backBtn.setBackground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(new CompoundBorder(new LineBorder(new Color(209, 213, 219)), new EmptyBorder(8, 15, 8, 15)));
        
        /** [기능 포인트] 화면 전환 액션
         * - 메인 페이지 인스턴스를 통해 주유소 리스트로 복귀
         */
        backBtn.addActionListener(e -> {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win instanceof MainPage) ((MainPage) win).showStationList();
        });

        p.add(title, BorderLayout.WEST);
        p.add(backBtn, BorderLayout.EAST);
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        return p;
    }

    private JPanel createBasicInfoCard(String name) {
        JPanel card = createBaseCard("🔵 기본 정보");
        JLabel stationTitle = new JLabel(name, SwingConstants.CENTER);
        stationTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        stationTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addrLabel = new JLabel("서울시 강남구 역삼동 123-45", SwingConstants.CENTER);
        addrLabel.setForeground(Color.GRAY);
        addrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel grid = new JPanel(new GridLayout(1, 2, 20, 0));
        grid.setOpaque(false);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        grid.add(createSubInfoBox("영업시간", "24시간"));
        grid.add(createSubInfoBox("전화번호", "02-1234-5678"));

        card.add(stationTitle);
        card.add(Box.createVerticalStrut(10));
        card.add(addrLabel);
        card.add(Box.createVerticalStrut(25));
        card.add(grid);
        return card;
    }

    private JPanel createPriceInfoCard() {
        JPanel card = createBaseCard("💲 유가 정보");
        JPanel grid = new JPanel(new GridLayout(1, 3, 20, 0));
        grid.setOpaque(false);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        
        grid.add(createPriceDetailBox("휘발유", "1,550원", "전국 평균 대비 -30원"));
        grid.add(createPriceDetailBox("경유", "1,450원", "전국 평균 대비 -20원"));
        grid.add(createPriceDetailBox("LPG", "950원", "전국 평균 대비 -10원"));

        JLabel updateLabel = new JLabel("* 최종 업데이트: 2026-01-27 09:30", SwingConstants.CENTER);
        updateLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        updateLabel.setForeground(Color.LIGHT_GRAY);
        updateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(grid);
        card.add(Box.createVerticalStrut(15));
        card.add(updateLabel);
        return card;
    }

    private JPanel createDistanceCostCard() {
        JPanel card = createBaseCard("🚩 거리 / 예상 이동 비용");
        JPanel grid = new JPanel(new GridLayout(1, 2, 20, 0));
        grid.setOpaque(false);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        grid.add(createSubInfoBox("현재 위치에서 거리", "1.5km"));
        grid.add(createSubInfoBox("예상 이동 비용", "약 300원 (연비 12km/L 기준)"));
        card.add(grid);
        return card;
    }

    /* ===== 공통 UI 빌더 메서드 ===== */

    private JPanel createBaseCard(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(new Color(225, 228, 232)), new EmptyBorder(30, 40, 30, 40)));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        t.setForeground(new Color(37, 99, 235));
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(t); p.add(Box.createVerticalStrut(20));
        return p;
    }

    private JPanel createSubInfoBox(String title, String value) {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(new Color(235, 237, 240)), new EmptyBorder(15, 20, 15, 20)));
        JLabel t = new JLabel(title);
        t.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        t.setForeground(Color.GRAY);
        JLabel v = new JLabel(value);
        v.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        p.add(t); p.add(v);
        return p;
    }

    private JPanel createPriceDetailBox(String type, String price, String compare) {
        JPanel p = new JPanel(new GridLayout(3, 1, 0, 3));
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(new Color(235, 237, 240)), new EmptyBorder(15, 20, 15, 20)));
        JLabel t = new JLabel(type); t.setForeground(Color.GRAY);
        JLabel v = new JLabel(price); v.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22)); v.setForeground(new Color(37, 99, 235));
        JLabel c = new JLabel(compare); c.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12)); c.setForeground(new Color(59, 130, 246));
        p.add(t); p.add(v); p.add(c);
        return p;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}