package IndivProject;
 
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
 
@Named(value = "mcBean")
@SessionScoped
public class MultipleChoiceJSFBean implements Serializable {
 
    private Statement pstmt;
    private String output;
    private String singleQuestionOutput;
    private String debug;
    private Boolean renderCondition = false;
 
    private static String userName = "tim";
    private static String passWord = "123";
 
    private static String username;
    private static String chapterNo;
    private static String sectionNumber;
    private static String questionNo;
    private static String question;
    private static String choiceA;
    private static String choiceB;
    private static String choiceC;
    private static String choiceD;
    private static String choiceE;
    private static String hint;
    private static String answerKey;
    private static String checkBoxValues;
    private static String radioButtonValue;
    private static int questionCount;
    private static String checkAnswerBtn;
 
    private static int currentQuestionNumber;
 
    private static ArrayList<String> usersAnswers;
 
    private static String temp = "";
    private static int userResult;
 
    private static String[] arr;
    private static String response;
 
    private int chapterSelection;
    private String[] chapters = new String[44];
 
//    public int fillArr() {
//        for (int i = 0; i < 44; i++) {
//            String num = Integer.toString(i + 1);
//            getChapters()[i] = "Chapter " + num;
//        }
//       
//        for (int j = 0; j < getChapters().length; j++){
//            System.out.println(getChapters()[j]);
//        }
//        return 0;
//    }
    public String[] getChapters() {
        return chapters;
    }
 
    public void setChapters(String[] chapters) {
        this.chapters = chapters;
    }
 
    public int getChapterSelection() {
        return chapterSelection;
    }
 
    public void setChapterSelection(int chapterSelection) {
        this.chapterSelection = chapterSelection;
    }
 
    public void setRenderCondition(Boolean render) {
        this.renderCondition = render;
    }
 
    public boolean getRenderCondition() {
        return renderCondition;
    }
 
    public void setOutput(String output) {
        this.output = output;
    }
 
    public String getOutput() {
        return output;
    }
 
    public void setsingleQuestionOutput(String setsingleQuestionOutput) {
        this.singleQuestionOutput = setsingleQuestionOutput;
    }
 
    public String getsingleQuestionOutput() {
        return singleQuestionOutput;
    }
 
    public String backToQuiz() {
        return "HomePage";
    }
 
    public void setDebug(String listString) {
        this.debug = listString;
    }
 
    public String getDebug() {
        return debug;
    }
 
    public void view() throws ClassNotFoundException, Exception {
        response = ("");
        String oneQuestion = "<input type=\"submit\" value=\"Check Answer for All Questions\" style=\"background: goldenrod; color: white\"/> </div>";
        initializeJdbc();
        ResultSet rs = null;
        try {
            //String query = ("SELECT * FROM intro10equiz WHERE chapterNo= '" + 1 + "' AND questionNo= '" + 2 + "' ;");
            String query = ("SELECT * FROM intro10equiz WHERE chapterNo= '" + chapterSelection + "' ;");
            rs = pstmt.executeQuery(query);
 
            if (!rs.next()) {
                response = (" Error: chapter number not found");
            } else {
                rs = pstmt.executeQuery(query);
                questionCount = 0;
                while (rs.next()) {
 
                    int chapterCol = rs.getInt("chapterNo");
                    chapterNo = Integer.toString(chapterCol);
                    //System.out.println(chapterNo);
 
                    int questionNoCol = rs.getInt("questionNo");
                    questionNo = Integer.toString(questionNoCol);
                    //System.out.println(questionNo);
 
                    question = rs.getString("question");
                    //System.out.println(question);
                    //oneQuestion = oneQuestion + "<p class=\"question\">" + questionNo + ". ";
                    oneQuestion = oneQuestion + "<p> " + question + "</p>\n";
 
                    choiceA = rs.getString("choiceA");
                    //System.out.println(choiceA);
                    //oneQuestion = oneQuestion + "<input type=\"radio\" name=\"q1\" value=\"a\" id=\"q1a\"><label for=\"q1a\">" + choiceA + "</label><br/>\n";
                    if (choiceA.startsWith("a") && !choiceA.startsWith("a.")) {
                        choiceA = choiceA.substring(1, choiceA.length() - 1);
                    }
                    if (choiceA.startsWith("a.") || choiceA.startsWith("A.")) {
                        choiceA = choiceA.substring(choiceA.indexOf("a.") + 2, choiceA.length());
                    }
                    oneQuestion = oneQuestion + "<input type=\"radio\" value=\"A\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">A.</span> <span id=\"choicestatement\">" + choiceA.trim() + "</span><br>";
 
                    choiceB = rs.getString("choiceB");
                    //System.out.println(choiceB);
                    if (choiceB.startsWith("b.") || choiceB.startsWith("B.")) {
                        choiceB = choiceB.substring(2, choiceB.length());
                    }
                    oneQuestion = oneQuestion + "<input type=\"radio\" value=\"B\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">B.</span> <span id=\"choicestatement\">" + choiceB.trim() + "</span><br>";
 
                    choiceC = rs.getString("choiceC");
                    //System.out.println(choiceC);
 
                    if (!rs.wasNull() && !choiceC.contains("null")) {
                        if (choiceC.startsWith("c.") || choiceC.startsWith("C.")) {
                            choiceC = choiceC.substring(2, choiceC.length());
                        }
                        oneQuestion = oneQuestion + "<input type=\"radio\" value=\"C\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">C.</span> <span id=\"choicestatement\">" + choiceC.trim() + "</span><br>";
                    }
                    choiceD = rs.getString("choiceD");
                    //System.out.println(choiceD);
 
                    if (!rs.wasNull() && !choiceD.contains("null")) {
                        if (choiceD.startsWith("d.") || choiceD.startsWith("D.")) {
                            choiceD = choiceD.substring(2, choiceD.length());
                        }
                        oneQuestion = oneQuestion + "<input type=\"radio\" value=\"D\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">D.</span> <span id=\"choicestatement\">" + choiceD.trim() + "</span><br>";
                    }
                    choiceE = rs.getString("choiceE");
                    //System.out.println(choiceE);
 
                    if (!rs.wasNull() && !choiceE.contains("null")) {
                        if (choiceE.startsWith("e.") || choiceE.startsWith("E.")) {
                            choiceE = choiceE.substring(2, choiceE.length());
                        }
                        oneQuestion = oneQuestion + "<input type=\"radio\" value=\"E\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">E.</span> <span id=\"choicestatement\">" + choiceE.trim() + "</span><br>";
                    }
                    answerKey = rs.getString("answerKey");
                   // System.out.println(answerKey);
 
                    hint = rs.getString("hint");
                    //System.out.println(hint);
 
                    response = (" View Successful");
                    oneQuestion = oneQuestion + "<div style=\"text-align: left; margin-right: 1em\"><input type=\"submit\" style=\"background: green; "
                            + "color: white; font-size: 85%; margin-top: 5px; margin-left: 5px; border: none\" name = \"button" + questionNo + "\" value= \" "
                            + "Check Answer for Question " + questionNo + "\"> </div>";
 
                    questionCount++;
                }
            }
 
            oneQuestion += "<div style=\"text-align: right; margin-right: 1em\"><input type=\"submit\" value=\"Check Answer for All Questions\" style=\"background: goldenrod; color: white\"/> </div>";
 
        } catch (SQLException ex) {
            response = (" Error: " + ex.getMessage());
        }
 
        //String outPutQuestion = "<h:outputText value=\"" + oneQuestion + "\" escape=\"false\"/>";
        //output = outPutQuestion;
        setOutput(oneQuestion);
        renderCondition = true;
 
        //return (chapterNo + "<br> " +questionNo + "<br> " +question + "<br> " +choiceA + "<br> " +choiceB + "<br> " +
        //choiceC + "<br> " +choiceD + "<br> " +choiceE + "<br> " +answerKey + "<br> " +hint);
        //return (outPutQuestion);
    }
 
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String viewForOne(int questionNumber) throws ClassNotFoundException, Exception {
 
        String rbValue = Integer.toString(questionNumber);
 
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        rbValue = request.getParameter("q" + questionNumber);
//        for (int i = 1; i < questionCount; i++) {
//            //all radio buttons for question 1 are named: q1, q1, q1, q1
//            String rbValue = request.getParameter("q" + i);
//            if (rbValue != null) {
//                //setRadioButtonValue(rbValue);
//               
//                //add user answer to question number and rbValue
////                usersAnswers.add(i - 1, rbValue);
//               
//                if (usersAnswers.get(0) != null) aChecked = usersAnswers.get(0);
//                if (usersAnswers.get(1) != null) bChecked = usersAnswers.get(1);
//                if (usersAnswers.get(2) != null) cChecked = usersAnswers.get(2);
//                if (usersAnswers.get(3) != null) dChecked = usersAnswers.get(3);
//                if (usersAnswers.get(4) != null) eChecked = usersAnswers.get(4);
//
//            }
//        }
//        String listString = String.join(", ", usersAnswers);
//        setDebug(listString);
        response = ("");
        String oneQuestion = "";
        initializeJdbc();
        ResultSet rs = null;
        try {
            //String query = ("SELECT * FROM intro10equiz WHERE chapterNo= '" + 1 + "' AND questionNo= '" + 2 + "' ;");
            String query = ("SELECT * FROM intro10equiz WHERE chapterNo= '" + chapterSelection + "' AND questionNo= '" + questionNumber + "' ;");
            rs = pstmt.executeQuery(query);
 
            if (!rs.next()) {
                response = (" Error: chapter number not found");
            } else {
                rs = pstmt.executeQuery(query);
                questionCount = 0;
                while (rs.next()) {
 
                    int chapterCol = rs.getInt("chapterNo");
                    chapterNo = Integer.toString(chapterCol);
                    //System.out.println(chapterNo);
 
                    int questionNoCol = rs.getInt("questionNo");
                    questionNo = Integer.toString(questionNoCol);
                    //System.out.println(questionNo);
 
                    question = rs.getString("question");
                    //System.out.println(question);
                    //oneQuestion = oneQuestion + "<p class=\"question\">" + questionNo + ". ";
                    oneQuestion = oneQuestion + "<p> " + question + "</p>\n";
 
                    choiceA = rs.getString("choiceA");
                    //System.out.println(choiceA);
                    //oneQuestion = oneQuestion + "<input type=\"radio\" name=\"q1\" value=\"a\" id=\"q1a\"><label for=\"q1a\">" + choiceA + "</label><br/>\n";
                    if (choiceA.startsWith("a") && !choiceA.startsWith("a.")) {
                        choiceA = choiceA.substring(1, choiceA.length() - 1);
                    }
                    if (choiceA.startsWith("a.") || choiceA.startsWith("A.")) {
                        choiceA = choiceA.substring(choiceA.indexOf("a.") + 2, choiceA.length());
                    }
 
                    /////////////////////////
                    String radioCheckA = "";
                    if (rbValue.equalsIgnoreCase("A")) {
                        radioCheckA = "checked=\"checked\"";
                    }
 
                    oneQuestion = oneQuestion + "<input type=\"radio\"" + radioCheckA + "value=\"A\"    name=\"q" + questionNo + "\"> <span id=\"choicelabel\">A.</span> <span id=\"choicestatement\">" + choiceA.trim() + "</span><br>";
 
                    choiceB = rs.getString("choiceB");
                    //System.out.println(choiceB);
                    if (choiceB.startsWith("b.") || choiceB.startsWith("B.")) {
                        choiceB = choiceB.substring(2, choiceB.length());
                    }
 
                    ////////////////////////
                    String radioCheckB = "";
                    if (rbValue.equalsIgnoreCase("B")) {
                        radioCheckB = "checked=\"checked\"";
                    }
 
                    oneQuestion = oneQuestion + "<input type=\"radio\"" + radioCheckB + " value=\"B\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">B.</span> <span id=\"choicestatement\">" + choiceB.trim() + "</span><br>";
 
                    choiceC = rs.getString("choiceC");
                    //System.out.println(choiceC);
 
                    if (!rs.wasNull() && !choiceC.contains("null")) {
                        if (choiceC.startsWith("c.") || choiceC.startsWith("C.")) {
                            choiceC = choiceC.substring(2, choiceC.length());
                        }
 
                        /////////////////////////
                        String radioCheckC = "";
                        if (rbValue.equalsIgnoreCase("C")) {
                            radioCheckC = "checked=\"checked\"";
                        }
 
                        oneQuestion = oneQuestion + "<input type=\"radio\"" + radioCheckC + " value=\"C\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">C.</span> <span id=\"choicestatement\">" + choiceC.trim() + "</span><br>";
                    }
                    choiceD = rs.getString("choiceD");
                    //System.out.println(choiceD);
 
                    if (!rs.wasNull() && !choiceD.contains("null")) {
                        if (choiceD.startsWith("d.") || choiceD.startsWith("D.")) {
                            choiceD = choiceD.substring(2, choiceD.length());
                        }
 
                        ///////////////////////
                        String radioCheckD = "";
                        if (rbValue.equalsIgnoreCase("D")) {
                            radioCheckD = "checked=\"checked\"";
                        }
 
                        oneQuestion = oneQuestion + "<input type=\"radio\"" + radioCheckD + " value=\"D\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">D.</span> <span id=\"choicestatement\">" + choiceD.trim() + "</span><br>";
                    }
                    choiceE = rs.getString("choiceE");
                   //System.out.println(choiceE);
 
                    if (!rs.wasNull() && !choiceE.contains("null")) {
                        if (choiceE.startsWith("e.") || choiceE.startsWith("E.")) {
                            choiceE = choiceE.substring(2, choiceE.length());
                        }
 
                        String radioCheckE = "";
                        if (rbValue.equalsIgnoreCase("E")) {
                            radioCheckE = "checked=\"checked\"";
                        }
 
                        oneQuestion = oneQuestion + "<input type=\"radio\"" + radioCheckE + " value=\"E\" name=\"q" + questionNo + "\"> <span id=\"choicelabel\">E.</span> <span id=\"choicestatement\">" + choiceE.trim() + "</span><br>";
                    }
                    answerKey = rs.getString("answerKey");
                    //System.out.println(answerKey);
 
                    hint = rs.getString("hint");
                    //System.out.println(hint);
 
                }
            }
 
        } catch (SQLException ex) {
            response = (" Error: " + ex.getMessage());
        }
        //setsingleQuestionOutput(oneQuestion);
 
        return oneQuestion;
 
    }
 
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initializeJdbc() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
 
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/selftest", "wilhsy86", "!@#310998007");
            Connection conn = DriverManager.getConnection("jdbc:mysql://csci-3720-p2.cehwxsgs7qjg.us-east-1.rds.amazonaws.com:3306/selftest", "wilhsy86", "310998007");
            pstmt = conn.createStatement();
            System.out.println("Database connected");
            System.out.println();
        } catch (SQLException | ClassNotFoundException ex) {
            response = (" Error: " + ex.getMessage());
        }
    }
 
    public void getSelectedRadioButtonValue() {
        radioButtonValue = "";
 
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        for (int i = 1; i < questionCount; i++) {
            //all radio buttons for question 1 are named: q1, q1, q1, q1
            String rbValue = request.getParameter("q" + i);
            if (rbValue != null) {
                //setRadioButtonValue(rbValue);
                FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "singleAnswer.xhtml");
                //add user answer to question number and rbValue
                //usersAnswers.add(i - 1, rbValue);
            }
        }
    }
 
    public String getQuestionForButtonPressed() throws Exception {
        checkAnswerBtn = "";
        try {
            //HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
           
            String urlParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().toString();
            System.out.println(urlParam);
            int buttonClicked = urlParam.indexOf("button");
            System.out.println(buttonClicked);
            System.out.println("charat " + urlParam.charAt(buttonClicked + 6));
           
            String temp = "" + urlParam.charAt(buttonClicked + 6);
            String temp2 =  urlParam.substring( (buttonClicked + 6) , ((urlParam.indexOf(' ', buttonClicked + 6)) - 1 ));
           
            System.out.println("temp  " + temp);
            System.out.println("temp 2 " + temp2);
           
            int currentButtonSubmitted = Integer.parseInt(temp2);
            System.out.println("!!!!!!!!!current button submitted " + currentButtonSubmitted);
           
            
            if (urlParam.contains("q" + currentButtonSubmitted)) {
                return viewForOne(currentButtonSubmitted);
                //if urlParam does not contain {q + question number then question wasnt answered this works for checkboxes and radio buttons
            } else {
                 return "Question " + currentButtonSubmitted + " has not been answered. Please try again.";
            }              
        } catch (NullPointerException ex) {
            //means explanation button was pressed just return same return statement....
           
        }
        return viewForOne(currentQuestionNumber);
    }
 
//       //======================GET SELECTED CHECK BOXES=================\\
//      
//       //all checkboxes are named in the format q1cb1 ...so question1 checkbox1, q1cb2...etc.
//           public void getSelectedCheckBoxesValues() {
//        checkBoxValues = ""; //reinitialize checkboxvalues
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        for (int i = 1; i < Integer.valueOf(chapterNo) + 1; i++) {
//            String cb1Value = request.getParameter("q" + (i) + "cb1");
//            String cb2Value = request.getParameter("q" + (i) + "cb2");
//            String cb3Value = request.getParameter("q" + (i) + "cb3");
//            String cb4Value = request.getParameter("q" + (i) + "cb4");
//            String cb5Value = request.getParameter("q" + (i) + "cb5");
//
//            if (cb1Value == null) {
//                cb1Value = "";
//            } else {
//                checkBoxValues += cb1Value;
//            }
//            if (cb2Value == null) {
//                cb2Value = "";
//            } else {
//                checkBoxValues += cb2Value;
//            }
//            if (cb3Value == null) {
//                cb3Value = "";
//            } else {
//                checkBoxValues += cb3Value;
//            }
//            if (cb4Value == null) {
//                cb4Value = "";
//            } else {
//                checkBoxValues += cb4Value;
//            }
//            if (cb5Value == null) {
//                cb5Value = "";
//            } else {
//                checkBoxValues += cb5Value;
//            }
//            setCheckBoxValues(checkBoxValues);
//            usersAnswers.add(i - 1, checkBoxValues);
//        }
//    }
}