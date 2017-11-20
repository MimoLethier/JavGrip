# JavGrip

A JavaFX Scene Decorator including Popup windows, Configuration Properties...

JavGrip proposes a few "basic" tools written to ease taking a first grip on JavaFX :
  Popup window enabling information, alerts, and alternative settlement (yes/no...)
  Basic Logger 
  Basic access to Configuration properties
  Scene Framer,  a bit fancier and customisable FX Stage Decoration, to be pluged between the PrimaryStage and its
  usual fxml Scene...
  
The main purpose, obviously, is not to replace more mature/richly featured/tested in depth/portable anywhere/standard 
tools, but to provide examples of JavaFX/FXML/CSS interactions, flexibility and power... kind of playground 
for initial experimentations and learning by examples. Nothing for the experimented JavaFX expert here, for sure!

JavGrip Demo integrates those tools in a working App that does pretty nothing, apart from showing a chocolate-framed 
window with an exit ("Bye...") and an Iconify ("Zzz...") buttons, and a notification Popup (click the Bye button...). 
Config is there, but needs a config file (Demo uses only default properties); similarly, a log is produced, but quite
a hollow one...

Tools may be constructed as Library packages, and Demo as a Main JAVAFX with FXML application...
