import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
public class Scrabble extends JFrame implements ActionListener{
//a class to create find a word board with buttons
   //intstancces
   
   private ArrayList<String> instantPos = new ArrayList<String>();
   private GridLayout gridlayout;
   private TileGUI[] tiles; 
   private String[] words;
   private Icon usedButton;
   private String n1,w,o;
   private char s;
   private TileGUI tile;
   private TileCollection collection;
   private JPanel GameControl,gridPanel;
   private JButton[] b;
   private int i,prev=-1,next=-1;
   private JLabel selTiles  = new JLabel("<html><span style='font-size:25px'>"+"WORD"+"</span></html>");   
   private JLabel score  = new JLabel("<html><span style='font-size:25px'>"+"SCORE"+"</span></html>");
   private JTextField word = new JTextField(30);
   private Font font = new Font("Courier", Font.BOLD,25);
   private JTextField scoreField = new JTextField(20);
   private JButton clear,enter,newGame, help;
   private int scoreWord=0,instantScore=0;
   
   public Scrabble(){
      super("Scrabble");//Title of window
   //first 3 lines are a usual way to start the JFrame Window
   //setTitle("Scrabble");
      setSize(610,700);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   //4 panels, a top one for showing the word being created, the other the grid
      GameControl = new JPanel();
      gridPanel = new JPanel();
   
   //adding panels into boarderLayout
      setLayout(new BorderLayout());
      add(GameControl, BorderLayout.NORTH); 
      add(gridPanel, BorderLayout.CENTER);
      
//icon for the task bar 
      ImageIcon img = new ImageIcon("scrabble.png");
      setIconImage(img.getImage());

//WORDPANEL NORTH  
      GameControl.setLayout(new GridLayout(4,2));
      
      GameControl.add(score);
      scoreField.setFont(font);
      scoreField.setEditable(false); //limits users from tempering with score
      GameControl.add(scoreField);
   
   //creating and adding buttons to action buttons to center panel for words
      
      Icon newGIcon = new ImageIcon(getClass().getResource("Scrabble_icon.png"));
      newGame = new JButton("NEW GAME",newGIcon);
      newGame.addActionListener(this); 
      GameControl.add(newGame);
      
      Icon helpIcon = new ImageIcon(getClass().getResource("help.png"));
      help = new JButton("HELP",helpIcon);
      help.addActionListener(this); 
      GameControl.add(help);
      
      Icon c = new ImageIcon(getClass().getResource("undo_enter.png"));
      Icon cc = new ImageIcon(getClass().getResource("clearr1.png"));
      clear = new JButton("CLEAR",c);
      clear.setRolloverIcon(cc);
      clear.addActionListener(this);
      GameControl.add(clear);
      
      Icon entr = new ImageIcon(getClass().getResource("enter.png"));
      enter = new JButton("ENTER",entr);
      enter.addActionListener(this); 
      GameControl.add(enter);
      
      GameControl.add(selTiles);
      word.setFont(font);
      GameControl.add(word);
      
//GRIDPANEL CENTER   
   //creating grid for letters for game
      gridlayout =  new GridLayout(6,6);
      gridPanel.setLayout(gridlayout);
      b = new JButton[36];
      tiles = new TileGUI[36];
      for(i = 0; i<b.length;i++){
            
            collection = new TileCollection(); //geting collection object for scrabbled tiles
            tile = new TileGUI(collection.removeOne()); //removes one tile at a time from collection
            b[i] = tile;
            gridPanel.add(b[i]);
            tiles[i] = tile;
            b[i].addActionListener(this); // this object has actionPerformed method   
            b[i].setActionCommand(""+i); 
         }
//Reading words from textfile and store them on a list of words so they are easier to access with less memory
      try{
          words = FileToArray.read("EnglishWords.txt");
          }
       catch(Exception e)
       {
         System.out.println("Could not find file!");
       }
   }
public void actionPerformed(ActionEvent event){
	String clicked = event.getActionCommand();
   System.out.println(clicked);
   if (clicked == "CLEAR")
   {
      
      for(String z:instantPos)
        {
         tiles[Integer.parseInt(z)].setIcon(null);
        }
      word.setText("");
      instantPos.removeAll(instantPos);
    prev = -1;
    }
   else if (clicked == "HELP")
   {
       String spacer = "         ";
         JPanel panel = new JPanel();
         JOptionPane.showMessageDialog(panel,"Game Rules:\nPresented with a 6x6 grid of Scrabble tiles, the challenge is to form as many high scoring words as possible. The tiles must form words which,\nin crossword fashion, flow left to right in rows or downwards in columns. The words must be defined in the standard dictionary.\n"+spacer+"* Words may only be formed from sequences of adjacent tiles.\n" +spacer+"* Two tiles are adjacent if their edges or corners meet.\n" +spacer+"* A tile may be used in at most one word. (Tiles appearing red are used tiles)\n","Rules",JOptionPane.INFORMATION_MESSAGE, null);
   }
    
   else if (clicked == "NEW GAME")
   {  int dialogMessage = JOptionPane.YES_NO_OPTION;
      int dialogMessage1 = JOptionPane.showConfirmDialog(this,"Are you sure you want to start a new game?","Restart Caution",dialogMessage);
      if (dialogMessage1 == 0){
         dispose(); 
         Scrabble newGG = new Scrabble();
         newGG.setVisible(true);
       }
   } 
   else if(clicked == "ENTER")
   {
     w = word.getText();
     System.out.println(w);
     boolean found = false;
     for(String n:words)
     {  //System.out.println(n); dubugging purposes
        if(w.equalsIgnoreCase(n))
        {   found = true;
            word.setText(word.getText()+" "+"Word Found!");
            scoreWord+=instantScore;
            scoreField.setText(Integer.toString(scoreWord));

            for(String v: instantPos)
            {
               b[Integer.parseInt(v)].setEnabled(false);
            }
            prev = -1;
            // try 
//             {     
//                   Thread.sleep(3000);
//                   word.setText("");                 //1000 milliseconds is one second.
//             } 
//             catch(InterruptedException ex) 
//             {
//                  Thread.currentThread().interrupt();
//             }
            break;
            
        }
     }
     instantScore = 0;
     if(found == false)
     {  
        word.setText(word.getText()+" "+"Word Not Found!");
        for(String q:instantPos)
        {
         tiles[Integer.parseInt(q)].setIcon(null);
        }
      prev=-1;
      //System.out.println("kkkk");
     }
     instantPos.removeAll(instantPos);
   }
   else{
      if (prev == -1){ 
      
         instantPos.add(clicked);
         usedButton = new ImageIcon("used_icon.png");
         tiles[Integer.parseInt(clicked)].setIcon(usedButton);
         System.out.println("Size: "+instantPos.size());
         s = tiles[Integer.parseInt(clicked)].getTile().letter(); //takes the letter on the button
         word.setText(word.getText() + s );
         instantScore+=tiles[Integer.parseInt(clicked)].getTile().value(); // takes the number assigned to letter for score purposes
         System.out.println(instantScore);
         prev = Integer.parseInt(clicked);
         
      }
      else if (instantPos.contains(clicked))
         {}
         else
         {
            next = Integer.parseInt(clicked);
            if (next == prev+1 || next == prev+5|| next == prev+6 || next == prev+7 || next == prev-1 || next ==prev-5 || next == prev-6 || next == prev-7) //conditions around next button
            {
               instantPos.add(clicked);
               usedButton = new ImageIcon("used_icon.png");
               tiles[Integer.parseInt(clicked)].setIcon(usedButton);
               System.out.println("Size: "+instantPos.size()); //debugging purposes
               s = tiles[Integer.parseInt(clicked)].getTile().letter(); //takes the letter on the button
               word.setText(word.getText() + s );
               instantScore+=tiles[Integer.parseInt(clicked)].getTile().value(); // takes the number assigned to letter for score purposes
               System.out.println(instantScore);
               prev = Integer.parseInt(clicked);
            }
            else{}
         }
   }    
}
   
public static void main(String[] args){
      Scrabble s = new Scrabble();
      s.setVisible(true);

   }
 
}