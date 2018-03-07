///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package IndivProject;
//
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpServletRequest;
//
///**
// *
// * @author WiLhS
// */
//public class postMethods {
//    
//        //===================GETTING URL PARAMETER=========================\\
//	
//	// Get Request Parameter value (Ex: http://localhost:8080/Grovenstein/faces/Quiz.xhtml?chapter=1, urlParam contains chapter=1, currentChapter = chapter1(format of txt files), chapterNumber=1)
//	//I have chapterList currently being populated by a textfile containing all the chapter number/titles.
//	// I use this to display the current chapter title according to whatever chapter was specified in the url.
//    public String getRequestParameter() {
//        //clear questionsList each new parameter
//        questionsList.clear();
//        //clear keyList each new parameter
//        keyList.clear();
//        //clear keyAndExplanationList each new parameter
//        keyAndExplanationList.clear();
//        try {
//            String urlParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().toString();
//            //urlParam = urlParam.replace("{", "").replace("}", "").replace("=", " ").replace("c", "C");
//            urlParam = urlParam.replaceAll("[^\\d]", "");
//            //check if a chapter was specified
//            if (urlParam.isEmpty()) {
//                return "Chapter not specified";
//            }
//            chapterNumber = Integer.parseInt(urlParam);
//            currentChapter = "chapter" + chapterNumber;
//            chapterNumber--; //for index purposes in chapterList
//        } catch (NumberFormatException ex) {
//            //empty catch due to null string when chapter is not specified
//        }
//        return chapterList.get(chapterNumber);
//    }
//	//=======================GET SELECTED RADIO BUTTON===============================\\
//	
//	//all radio buttons for question 1 are named: q1, q1, q1, q1 same is for every other radiobutton, naming is different for check boxes.
//	
//	    public void getSelectedRadioButtonValue() {
//        radioButtonValue = "";
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        for (int i = 1; i < questionsList.size() + 1; i++) {
//            //all radio buttons for question 1 are named: q1, q1, q1, q1
//            String rbValue = request.getParameter("q" + i);
//            if (rbValue != null) {
//                setRadioButtonValue(rbValue);
//                FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "IndividualQuestionView.xhtml");
//                //add user answer to question number and rbValue
//                usersAnswers.add(i - 1, rbValue);
//            }
//        }
//    }
//	
//	//======================GET SELECTED CHECK BOXES=================\\
//	
//	//all checkboxes are named in the format q1cb1 ...so question1 checkbox1, q1cb2...etc.
//	    public void getSelectedCheckBoxesValues() {
//        checkBoxValues = ""; //reinitialize checkboxvalues
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        for (int i = 1; i < questionsList.size() + 1; i++) {
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
//	
//	//======================DISPLAY QUESTION SPECIFIED FOR INDIVIDUAL VIEW================\\
//	
//	//checkUsersAnswersForIndividualView method in here just edits the String of the question html code and according to the users answer/answers adds "checked" so that itll be checked on the IndividualView
//
//    public String getQuestionForButtonPressed() {
//        String checkAnswerBtn = "";
//        try {
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            //check for which button isn't null (which means it was pressed)
//            for (int i = 1; i < questionsList.size(); i++) {
//                checkAnswerBtn = request.getParameter("btn" + i);
//                if (checkAnswerBtn != null) {
//                    break;
//                }
//            }
//            int questionNumberSubmitted = Integer.parseInt(checkAnswerBtn.replaceAll("[^\\d]", ""));
//            currentQuestionNumber = questionNumberSubmitted; //acutal question number need to subtract 1 from this for arraylist index purposes. q1 = 0
//            //if user didnt submit an answer don't return anything rightOrWrong() will handle this.
//            if (usersAnswers.get(currentQuestionNumber - 1).isEmpty()) {
//                return "";
//            }
//            //otherwise return the string containing radio/checkboxes with user selected values checked.
//            return checkUsersAnswersForIndividualView(currentQuestionNumber - 1); // questionNumberSubmitted - 1 because arraylist index starts from 0
//        } catch (NullPointerException ex) {
//            //means explanation button was pressed just return same return statement....
//            return checkUsersAnswersForIndividualView(currentQuestionNumber - 1); 
//        }
//    }
//	
//	//=================GET THE SUBMITBUTTON/RADIBUTTON/CHECKBOXES THAT ARE SUBMITTED ON POST(posting to IndividualQuestionView)================
//	
//	//I originally was using get so urlParam is just the values from the post.....
//	// the setRadioButtonValue and setCheckBoxValue are used here for debugging purposes, so you can see the format the buttons are being sent over to the next page(individualQuestionView)
//	
//	    public void getRequestParameterIndividualView() {
//        try {
//            String urlParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().toString();
//            //reinialize key for every question so its not displayed until button pressed
//            //this can be referenced as the question number
//            int currentButtonSubmitted = Integer.parseInt(urlParam.substring(urlParam.length() - 3, urlParam.length() - 1).replaceAll(" ", "")); // example:the btn "Check answer for question 1" => 1
//
//            //post param button contains "Click"" must be answer key button, I named the button to get the correct answer "Click for answer to question 1"
//            if (urlParam.contains("Click")) {
//                setKeyForCurrentQuestion(keyAndExplanationList.get(currentButtonSubmitted - 1));
//                //if urlParam does not contain {q + question number then question wasnt answered this works for checkboxes and radio buttons
//            } else {
//                setKeyForCurrentQuestion(""); // set key to empty so it doesn't show for next question
//            }
//            if (!urlParam.contains("q" + currentButtonSubmitted)) {
//                radioButtonValue = "Question was no answered" + "URL Param: " + urlParam;
//            } //radio buttons
//            else if (keyList.get(currentButtonSubmitted - 1).length() == 1) { // -1 for arraylist index purposes
//                int indexOfRadioButtonAnswer = urlParam.indexOf("q" + currentButtonSubmitted);
//                //index has to be two due to index shift when question number is two digits
//                String rbanswer = urlParam.substring(indexOfRadioButtonAnswer + 3, indexOfRadioButtonAnswer + 5).replaceAll("[^a-zA-Z]", ""); //replace all non letters
//                // if user has already answered set that user answer to empty adn replace with new anwswer
//                if (!usersAnswers.get(currentButtonSubmitted - 1).isEmpty()) {
//                    usersAnswers.remove(currentButtonSubmitted - 1);
//                }
//                usersAnswers.add(currentButtonSubmitted - 1, rbanswer);
//
//                //setRadioButtonValue(urlParam.substring(4,5));
//                //  radioButtonFormat in post is: {q1=a, btn1=Check Answer for Question 1}
//                // checkBoxFormat in post is: {q3cb1=a, q3cb2=b, q3cb3=c, q3cb4=d, btn3=Check Answer for Question 3}
//                // usersAnswers.add(0, urlParam);
//                setRadioButtonValue(urlParam + " CURRENT: " + currentButtonSubmitted + "RADIOBUTTONVALUE:" + rbanswer + " Current chapter: " + currentChapter + "KEY: " + keyForCurrentQuestion);
//                // setRadioButtonValue(rbanswer);
//                // usersAnswers.add(currentButtonSubmitted-1, rbanswer);
//                //checkboxes
//            } else if (keyList.get(currentButtonSubmitted - 1).length() > 1) {
//                String cbanswer = "";
//                int indexOfCheckBoxA = urlParam.indexOf("q" + currentButtonSubmitted + "cb1");
//                int indexOfCheckBoxB = urlParam.indexOf("q" + currentButtonSubmitted + "cb2");
//                int indexOfCheckBoxC = urlParam.indexOf("q" + currentButtonSubmitted + "cb3");
//                int indexOfCheckBoxD = urlParam.indexOf("q" + currentButtonSubmitted + "cb4");
//                int indexOfCheckBoxE = urlParam.indexOf("q" + currentButtonSubmitted + "cb5");
//                if (!usersAnswers.get(currentButtonSubmitted - 1).isEmpty()) {
//                    usersAnswers.remove(currentButtonSubmitted - 1);
//                }
//                if (indexOfCheckBoxA != -1) {
//                    cbanswer += urlParam.substring(indexOfCheckBoxA + 6, indexOfCheckBoxA + 8).replaceAll("[^a-zA-Z]", ""); //index is distance of two because of index shift when question
//                    //number is two digits.....aka q1cb1 and q14cb1 has different indexes to grab answer.
//                }
//                if (indexOfCheckBoxB != -1) {
//                    cbanswer += urlParam.substring(indexOfCheckBoxB + 6, indexOfCheckBoxB + 8).replaceAll("[^a-zA-Z]", "");
//                }
//                if (indexOfCheckBoxC != -1) {
//                    cbanswer += urlParam.substring(indexOfCheckBoxC + 6, indexOfCheckBoxC + 8).replaceAll("[^a-zA-Z]", "");
//                }
//                if (indexOfCheckBoxD != -1) {
//                    cbanswer += urlParam.substring(indexOfCheckBoxD + 6, indexOfCheckBoxD + 8).replaceAll("[^a-zA-Z]", "");
//                }
//                if (indexOfCheckBoxE != -1) {
//                    cbanswer += urlParam.substring(indexOfCheckBoxE + 6, indexOfCheckBoxE + 8).replaceAll("[^a-zA-Z]", "");
//                }
//                usersAnswers.add(currentButtonSubmitted - 1, cbanswer);
//                setCheckBoxValues("CB values selected: " + cbanswer + " Current chapter: " + currentChapter + " URLPARAM: " + urlParam + " CURRENT: " + currentButtonSubmitted);
//            }
//            // setRadioButtonValue(urlParam + " CURRENT: " + currentButtonSubmitted + "CheckBoxVALUEs:" + cbanswer);  
//        } catch (NumberFormatException ex) {
//            //empty catch due to null string when chapter is not specified
//        }
//    }
//    
//}
//
//
