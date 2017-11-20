/*
*  Created by MIMO on April 28th, 2016
*
*  CONTEXT :   Easing access to JAVAFX development
*  PURPOSE :   Enable using a little more appealing Application window framing than the basic Stage
*  ROLE    :	Provide a simple, basic "FRAMER" taking care of the Application Scene frame decoration.
*		Simple means NOT resizable, and NOT tested outside Windows 8 OS
*
*	Technical Notes :JuniorSceneFramer extends StackPane; it receives the parent STAGE through the Application START method, and the
*		User Application DECOR ContentTree (as a PANE type/subtype), recovered during the app FXML GUI load, or just build expressely before...
*		It permits installing a "Bye" button, which triggers an ActionEvent that can be handled by the App, a "Zzz" button, which toggle the Scene
*		ICONIFIED status, and a Nameplate, which display an Application Name. It supports DRAGGING the resulting window using the Scene borders.
*/


package sceneTools;


import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class JuniorSceneFramer extends StackPane
 {
   // Private Resources
   private Stage groundStage;
   private final Pane appDecorContent;
   private Scene appScene = null;
   private final Rectangle frameBackground;
   private final int frameBorderWidth = 4, frameTopHeigth = 25, frameSmallRounding = 25, frameLargeRounding = 30;
   private final double frameWidth, frameHeigth;
   private double stageX, stageY, moveInitX, moveInitY;
   private TextField frameTitleTXT = null;
   private Button iconifyBTN = null, exitBTN = null;

   // Public Resources
   public Menu exitTrigger;





//wwwwwwwwwwwwwwwwwwwwwwwwwww
//		MAINPROGRAMSECTION  0 -  CONSTRUCTION, INITIALIZATION, and TERMINATION
//wwwwwwwwwwwwwwwwwwwwwwwwwww

   // BASIC CONSTRUCTOR
   //
   public JuniorSceneFramer (Stage theStage, Pane theDecor)
    {
	groundStage = theStage;
	appDecorContent = theDecor;
	frameWidth = appDecorContent.getPrefWidth() + (2*frameBorderWidth);
	frameHeigth = appDecorContent.getPrefHeight() + frameTopHeigth + (3*frameBorderWidth);
	this.setBackground(Background.EMPTY);
	frameBackground = new Rectangle(frameWidth, frameHeigth, Color.SIENNA);
	frameBackground.setArcWidth(frameLargeRounding);
	frameBackground.setArcHeight(frameSmallRounding);
	this.getChildren().add(frameBackground);
	StackPane.setMargin(appDecorContent, new Insets(frameTopHeigth + 2, frameBorderWidth, (2*frameBorderWidth) - 2, frameBorderWidth));
	this.getChildren().add(appDecorContent);
	// Add a shadow item to propagate event when the Exit Button is clicked
	exitTrigger = new Menu("Exit");
	exitTrigger.setVisible(false);
	// Handle MOVING the window
	// 1. Disable Drag on APP : Dragging events observed in the AppDecore area are never passed further
	appDecorContent.setOnMouseDragged( (MouseEvent event) -> {
		   event.consume();
		});
	// 2. Register any MousePressed location in the Stageframe area, in case it is just the beginning of a Drag; also record the current Stage location...
	frameBackground.setOnMousePressed((MouseEvent event) -> {
		   stageX = groundStage.getX();
		   stageY = groundStage.getY();
		   moveInitX = event.getScreenX();
		   moveInitY = event.getScreenY();
		});
	// 3. React on Drag events triggered in the Stageframe-only area
	frameBackground.setOnMouseDragged((MouseEvent event) -> {
		   groundStage.setX(stageX + event.getScreenX() - moveInitX);
		   groundStage.setY(stageY + event.getScreenY() - moveInitY);
		});
	// 4. Close this Move when done and reset Stage "current" location
	frameBackground.setOnMouseReleased((MouseEvent event) -> {
		   stageX = groundStage.getX();
		   stageY = groundStage.getY();
		});
   }


   // SHORTCUT CONSTRUCTOR
   //
   public JuniorSceneFramer (Stage theStage, Pane theDecor, String theName)
    {
	this(theStage, theDecor);
	allowExitRequest();
	allowIconifyability();
	allowNamePlate(theName);
   }



//wwwwwwwwwwwwwwwwwwwwwwwwwww
//		MAINPROGRAMSECTION  1 - CORE BUSINESS : USER SERVICES, and INTERACTIONS HANDLING
//wwwwwwwwwwwwwwwwwwwwwwwwwww

   // Add an EXIT Button (just once)
   //
   public final void allowExitRequest ()
    {
	if ( exitBTN == null ) {
	   exitBTN = new Button("Bye...");
	   exitBTN.setPrefSize(50, frameTopHeigth - 2);
	   exitBTN.setStyle("-fx-background-color: chocolate;-fx-text-fill: maroon;-fx-border-width: 0pt;");
	   exitBTN.setFont(Font.font("Calibri", FontWeight.BOLD, 12.0));
	   exitBTN.setOnAction((ActionEvent event) -> {
		exitTrigger.fire();
		event.consume();  });
	   StackPane.setAlignment(exitBTN, Pos.TOP_LEFT);
	   this.getChildren().add(exitBTN);
	}
   }

   // Add an ICONIFY Button (just once)
   //
   public final void allowIconifyability ()
    {
	if ( iconifyBTN == null ) {
	   iconifyBTN = new Button("Zzz...");
	   iconifyBTN.setPrefSize(50, frameTopHeigth - 2);
	   iconifyBTN.setStyle("-fx-background-color: chocolate;-fx-text-fill: mediumblue;-fx-border-width: 0pt;");
	   iconifyBTN.setFont(Font.font("Calibri", FontWeight.BOLD, 12.0));
	   iconifyBTN.setOnAction( (ActionEvent event) -> {  groundStage.setIconified( ! groundStage.isIconified() );  });
	   StackPane.setAlignment(iconifyBTN, Pos.TOP_RIGHT);
	   this.getChildren().add(iconifyBTN);
	}
   }

   // Add a TITLE (just once)
   //
   public final void allowNamePlate (String theTitle)
    {
	if ( frameTitleTXT == null ) {
	   frameTitleTXT = new TextField();
	   frameTitleTXT.setPrefSize(500, frameTopHeigth);
	   frameTitleTXT.setBackground(Background.EMPTY);
	   frameTitleTXT.setFont(Font.font("Calibri Italic", FontWeight.BOLD, 14.0));
	   frameTitleTXT.setStyle("-fx-text-fill: orange;");
	   frameTitleTXT.setText(theTitle);
	   frameTitleTXT.setFocusTraversable(false);
	   frameTitleTXT.setMouseTransparent(true);
	   frameTitleTXT.setAlignment(Pos.CENTER);
	   StackPane.setAlignment(frameTitleTXT, Pos.TOP_CENTER);
	   this.getChildren().add(frameTitleTXT);
	}
   }

   // Change the Title
   //
   public void changeName (String theTitle)
    {
	frameTitleTXT.setText(theTitle);
   }


   // Create and return the SCENE... IFF NOT already set
   //
   public Scene getDecoratedScene ()
    {
	if ( appScene == null ) {
	   appScene = new Scene(this);
	   appScene.setFill(Color.TRANSPARENT);
	   groundStage.initStyle(StageStyle.TRANSPARENT);
	}
	return appScene;
   }

}
