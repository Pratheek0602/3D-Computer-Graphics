import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Spacecraft extends JFrame {
  
  private static final int WIDTH = 1024;
  private static final int HEIGHT = 768;
  private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
  private GLCanvas canvas;
  private Spacecraft_GLEventListener glEventListener;

  private JSlider worldLightSlider;
  private JSlider spotlightSlider;
  private JButton worldLightToggle;
  private JButton spotlightToggle;

  private final FPSAnimator animator; 

  public static void main(String[] args) {
    Spacecraft b1 = new Spacecraft("Spacecraft");
    b1.getContentPane().setPreferredSize(dimension);
    b1.pack();
    b1.setVisible(true);
    b1.canvas.requestFocusInWindow();
  }

  public Spacecraft(String textForTitleBar) {
    super(textForTitleBar);
    setUpCanvas();
    setUpUI();
    getContentPane().add(canvas, BorderLayout.CENTER);
    addWindowListener(new windowHandler());
    animator = new FPSAnimator(canvas, 60);
    animator.start();
  }

  private void setUpCanvas() {
    GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
    canvas = new GLCanvas(glcapabilities);
    Camera camera = new Camera(Camera.DEFAULT_POSITION,
        Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);
    glEventListener = new Spacecraft_GLEventListener(camera);
    canvas.addGLEventListener(glEventListener);
    canvas.addMouseMotionListener(new MyMouseInput(camera));
    canvas.addKeyListener(new MyKeyboardInput(camera));
  }

  private void setUpUI() {
    // Set the overall layout of the frame
    getContentPane().setLayout(new BorderLayout());
    // Add the OpenGL canvas to the center
    getContentPane().add(canvas, BorderLayout.CENTER);

    // Create the main control panel with horizontal BoxLayout
    JPanel mainControlPanel = new JPanel();
    mainControlPanel.setLayout(new BoxLayout(mainControlPanel, BoxLayout.X_AXIS));
    mainControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Top, Left, Bottom, Right padding

    // === Robot Control Panel ===
    JPanel robotControlPanel = new JPanel();
    robotControlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Align left with horizontal gap 10
    robotControlPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLACK, 2), "Robot Controls"));

    JButton robot1StartButton = new JButton("Robot1 Start");
    JButton robot1StopButton = new JButton("Robot1 Stop");
    JButton robot2StartButton = new JButton("Robot2 Start");
    JButton robot2StopButton = new JButton("Robot2 Stop");

    // Set smaller font for buttons
    Font buttonFont = new Font("Arial", Font.PLAIN, 12);
    robot1StartButton.setFont(buttonFont);
    robot1StopButton.setFont(buttonFont);
    robot2StartButton.setFont(buttonFont);
    robot2StopButton.setFont(buttonFont);

    // Add buttons to the robot control panel
    robotControlPanel.add(robot1StartButton);
    robotControlPanel.add(robot1StopButton);
    robotControlPanel.add(robot2StartButton);
    robotControlPanel.add(robot2StopButton);

    // === World Light Control Panel ===
    JPanel worldLightPanel = new JPanel();
    worldLightPanel.setLayout(new BoxLayout(worldLightPanel, BoxLayout.Y_AXIS));
    worldLightPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLACK, 2), "World Light"));

    // Label above the slider
    JLabel worldLightLabel = new JLabel("Adjust the intensity:");
    worldLightLabel.setFont(new Font("Arial", Font.PLAIN, 7));
    worldLightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Horizontal box for toggle button and slider
    JPanel worldLightControls = new JPanel();
    worldLightControls.setLayout(new BoxLayout(worldLightControls, BoxLayout.X_AXIS));
    worldLightControls.setAlignmentX(Component.CENTER_ALIGNMENT);

    worldLightToggle = new JButton("ON");
    worldLightToggle.setBackground(Color.GREEN);
    worldLightToggle.setFont(buttonFont);
    worldLightToggle.setPreferredSize(new Dimension(60, 25));
    worldLightToggle.setMaximumSize(new Dimension(60, 25));

    worldLightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
    worldLightSlider.setPreferredSize(new Dimension(100, 25)); // Smaller width
    worldLightSlider.setMaximumSize(new Dimension(100, 25));
    worldLightSlider.setMajorTickSpacing(20);
    worldLightSlider.setPaintTicks(true);
    // Remove tooltip as per user request

    // Add components with spacing
    worldLightControls.add(worldLightToggle);
    worldLightControls.add(Box.createRigidArea(new Dimension(10, 0))); // 10px horizontal space
    worldLightControls.add(worldLightSlider);

    // Add label and controls to the world light panel
    worldLightPanel.add(worldLightLabel);
    worldLightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 5px vertical space
    worldLightPanel.add(worldLightControls);

    // === Spotlight Control Panel ===
    JPanel spotlightPanel = new JPanel();
    spotlightPanel.setLayout(new BoxLayout(spotlightPanel, BoxLayout.Y_AXIS));
    spotlightPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLACK, 2), "Spotlight"));

    // Label above the slider
    JLabel spotlightLabel = new JLabel("Adjust the intensity:");
    spotlightLabel.setFont(new Font("Arial", Font.PLAIN, 7));
    spotlightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Horizontal box for toggle button and slider
    JPanel spotlightControls = new JPanel();
    spotlightControls.setLayout(new BoxLayout(spotlightControls, BoxLayout.X_AXIS));
    spotlightControls.setAlignmentX(Component.CENTER_ALIGNMENT);

    spotlightToggle = new JButton("ON");
    spotlightToggle.setBackground(Color.GREEN);
    spotlightToggle.setFont(buttonFont);
    spotlightToggle.setPreferredSize(new Dimension(60, 25));
    spotlightToggle.setMaximumSize(new Dimension(60, 25));

    spotlightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
    spotlightSlider.setPreferredSize(new Dimension(100, 25)); // Smaller width
    spotlightSlider.setMaximumSize(new Dimension(100, 25));
    spotlightSlider.setMajorTickSpacing(20);
    spotlightSlider.setPaintTicks(true);
    // Remove tooltip as per user request

    // Add components with spacing
    spotlightControls.add(spotlightToggle);
    spotlightControls.add(Box.createRigidArea(new Dimension(10, 0))); // 10px horizontal space
    spotlightControls.add(spotlightSlider);

    // Add label and controls to the spotlight panel
    spotlightPanel.add(spotlightLabel);
    spotlightPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 5px vertical space
    spotlightPanel.add(spotlightControls);

    // === Adding All Panels to Main Control Panel ===
    mainControlPanel.add(robotControlPanel);
    mainControlPanel.add(Box.createHorizontalStrut(20)); // Space between robot and lights
    mainControlPanel.add(worldLightPanel);
    mainControlPanel.add(Box.createHorizontalStrut(20)); // Space between lights
    mainControlPanel.add(spotlightPanel);

    // Add the main control panel to the frame at the bottom
    getContentPane().add(mainControlPanel, BorderLayout.SOUTH);

    // === Action Listeners ===

    // Robot1 Start Button
    robot1StartButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            glEventListener.startRobotDance();
        }
    });

    // Robot1 Stop Button
    robot1StopButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            glEventListener.stopRobotDance();
        }
    });

    // Robot2 Start Button
    robot2StartButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            glEventListener.moveRobot2();
        }
    });

    // Robot2 Stop Button
    robot2StopButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            glEventListener.stopRobot2();
        }
    });

    // World Light Toggle Listener
    worldLightToggle.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean isEnabled = glEventListener.toggleWorldLight();
            updateWorldLightControls(isEnabled);
        }
    });

    // World Light Slider Listener
    worldLightSlider.addChangeListener(e -> {
        float intensity = worldLightSlider.getValue() / 100.0f;
        glEventListener.setWorldLightIntensity(intensity);
    });

    // Spotlight Toggle Listener
    spotlightToggle.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean isEnabled = glEventListener.toggleSpotlight();
            updateSpotlightControls(isEnabled);
        }
    });

    // Spotlight Slider Listener
    spotlightSlider.addChangeListener(e -> {
        float intensity = spotlightSlider.getValue() / 100.0f;
        glEventListener.setSpotlightIntensity(intensity);
    });
  }

  // Helper methods to update control states
  private void updateWorldLightControls(boolean isEnabled) {
    worldLightToggle.setText(isEnabled ? "ON" : "OFF");
    worldLightToggle.setBackground(isEnabled ? Color.GREEN : Color.RED);
    worldLightSlider.setEnabled(isEnabled);
  }

  private void updateSpotlightControls(boolean isEnabled) {
    spotlightToggle.setText(isEnabled ? "ON" : "OFF");
    spotlightToggle.setBackground(isEnabled ? Color.GREEN : Color.RED);
    spotlightSlider.setEnabled(isEnabled);
  }

  private class windowHandler extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      animator.stop();
      remove(canvas);
      dispose();
      System.exit(0);
    }
  }
}

class MyKeyboardInput extends KeyAdapter  {
  private Camera camera;
  
  public MyKeyboardInput(Camera camera) {
    this.camera = camera;
  }
  
  public void keyPressed(KeyEvent e) {
    Camera.Movement m = Camera.Movement.NO_MOVEMENT;
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:  m = Camera.Movement.LEFT;  break;
      case KeyEvent.VK_RIGHT: m = Camera.Movement.RIGHT; break;
      case KeyEvent.VK_UP:    m = Camera.Movement.UP;    break;
      case KeyEvent.VK_DOWN:  m = Camera.Movement.DOWN;  break;
      case KeyEvent.VK_A:  m = Camera.Movement.FORWARD;  break;
      case KeyEvent.VK_Z:  m = Camera.Movement.BACK;  break;
    }
    camera.keyboardInput(m);
  }
}

class MyMouseInput extends MouseMotionAdapter {
  private Point lastpoint;
  private Camera camera;
  
  public MyMouseInput(Camera camera) {
    this.camera = camera;
  }
  
    /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */    
  public void mouseDragged(MouseEvent e) {
    Point ms = e.getPoint();
    float sensitivity = 0.001f;
    float dx=(float) (ms.x-lastpoint.x)*sensitivity;
    float dy=(float) (ms.y-lastpoint.y)*sensitivity;
    //System.out.println("dy,dy: "+dx+","+dy);
    if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK)
      camera.updateYawPitch(dx, -dy);
    lastpoint = ms;
  }

  /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */  
  public void mouseMoved(MouseEvent e) {   
    lastpoint = e.getPoint(); 
  }

}
