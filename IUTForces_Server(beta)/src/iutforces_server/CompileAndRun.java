/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutforces_server;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 * This class is responsible for compiling and running the source codes. 
 * @author Rifat
 */
public class CompileAndRun implements Runnable 
{
    final private NewProblem problem;
    final private NewSubmission submission;
    private ProcessBuilder compile, run, compare;
    private File inputs, expectedOutputs, userOutputs, submittedFile;
    final String folderPath;
    int submissionID;
    long timeTaken;
    Database db;
    /**
     * This is  the constructor for the CompileAndRun class.
     * The directory in which the submitted codes will be temporarily saved is named "CompilerDir".
     * @param problem The NewProblem class's object.
     * @param submission The NewSubmission class's object.
     * @param submissionID The unique ID of the submitted code.
     * @param db The object of the Database class.
     * 
     */
    public CompileAndRun(NewProblem problem, NewSubmission submission, int submissionID, Database db) 
    {
        this.problem = problem;
        this.submission = submission;
        this.submissionID = submissionID;
        this.db = db;
        this.timeTaken = -1;

        folderPath = "CompilerDir/";
        new File("CompilerDir").mkdir();

    }
    /**
     * This function is responsible for compiling C++ source codes.
     * A new file is created with the naming scheme submissionID + ".cpp" inside the folderPath directory.
     * The contents of the submission object is written on to the new .cpp file.
     * A ProcessBuilder object is created with the respective commands and directory as arguments.
     * The ProcessBuilder class constructs a process builder with the specified operating system program and arguments.
     * @return p.exitValue() The exit value of the compilation process.
     */
    private int compileCpp() 
    {
        submittedFile = new File(folderPath + submissionID + ".cpp");
        try {
            FileOutputStream fos = new FileOutputStream(submittedFile);
            fos.write(submission.getCodeF());
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("At CompileCPP FOS Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("At CompileCPP FWrite Error: " + ex.getMessage());
        }

        compile = new ProcessBuilder("g++", folderPath + submissionID + ".cpp", "-o", folderPath + submissionID);
        try {
            Process p = compile.start();
            try {
                p.waitFor(10, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                p.destroy();
                return -1;
            }
            return p.exitValue();

        } catch (IOException ex) {
            System.out.println("At CompileCPP CompileProcess Err " + ex.getMessage());
            return -2;
        }

    }
    /**
     * This function is responsible for compiling Java source codes.
     * A new folder is created with the naming scheme of just the submissionID.
     * A new file is created with the naming scheme submissionID + ".java" inside the submissionID folder.
     * The contents of the submission object is written on to the new .java file.
     * A ProcessBuilder object is created with the respective commands and directory as arguments.
     * The ProcessBuilder class constructs a process builder with the specified operating system program and arguments.
     * @return p.exitValue() The exit value of the compilation process.
     */
    private int compileJava() 
    {
        new File(Integer.toString(submissionID)).mkdir();
        submittedFile = new File(submissionID+"/"+ submissionID + ".java");
        try {
            FileOutputStream fos = new FileOutputStream(submittedFile);
            fos.write(submission.getCodeF());
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("At CompileJava FOS Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("At CompileJava FWrite Error: " + ex.getMessage());
        }

        compile = new ProcessBuilder("javac",submissionID + ".java").directory(new File(Integer.toString(submissionID)));
        try {
            Process p = compile.start();
            try {
                p.waitFor(10, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                p.destroy();
                return -1;
            }
            return p.exitValue();

        } catch (IOException ex) {
            System.out.println("At CompileJava CompileProcess Error: " + ex.getMessage());
            return -2;
        }

    }
    /**
     * This function is responsible for compiling C source codes.
     * A new file is created with the naming scheme submissionID + ".c" inside the folderPath directory.
     * The contents of the submission object is written on to the new .c file.
     * A ProcessBuilder object is created with the respective commands and directory as arguments.
     * The ProcessBuilder class constructs a process builder with the specified operating system program and arguments.
     * @return p.exitValue() The exit value of the compilation process.
     */
    private int compileC() 
    {
        submittedFile = new File(folderPath + submissionID + ".c");
        try {
            FileOutputStream fos = new FileOutputStream(submittedFile);
            fos.write(submission.getCodeF());
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("At CompileC FOS Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("At CompileC FWrite Error: " + ex.getMessage());
        }

        compile = new ProcessBuilder("gcc", folderPath + submissionID + ".c", "-o", folderPath + submissionID);
        try {
            Process p = compile.start();
            try {
                p.waitFor(10, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                p.destroy();
                return -1;
            }
            return p.exitValue();

        } catch (IOException ex) {
            System.out.println("At CompileC CompileProcess Error: " + ex.getMessage());
            return -2;
        }

    }
    /**
     * This function is responsible for running the codes written in C and C++.
     * The .exe file is run using a ProcessBuilder object. The inputs are kept in the inputs file and the outputs generated are kept in the userOutputs file.
     * The amount of time taken can be determined by subtracting the startTime from the stopTime. The time's unit, which is in nanoseconds is then converted to milliseconds.
     * @return p.exitValue() The exit value of the compilation process.
     */
    private int runCppC() 
    {
        run = new ProcessBuilder(folderPath + submissionID + ".exe");
        run.redirectInput(inputs);
        run.redirectOutput(userOutputs);
        System.out.println(Integer.parseInt(problem.getTimeLimit()));
        try {
            long startTime = System.nanoTime();
            Process p = run.start();
            try {
                if (!p.waitFor(Integer.parseInt(problem.getTimeLimit()), TimeUnit.MILLISECONDS)) 
                {
                    long stopTime = System.nanoTime();
                    p.destroy();
                    timeTaken = (stopTime - startTime)/1000000;
                    return -1;
                }
            } catch (InterruptedException ex) {
                System.out.println("At runCppC Runtime/TLE Error: " + ex.getMessage());
                return -1;
            }
            long stopTime = System.nanoTime();
            timeTaken = (stopTime-startTime)/1000000;
            return p.exitValue();

        } catch (IOException ex) {
            System.out.println("At runCppC Runtime Error: " + ex.getMessage());
            return -2;
        }
    }
    /**
     * This function is responsible for running the codes written in Java.
     * The list of files that have a .class extension is figured out. The first .class file run using the ProcessBuilder object.
     * The inputs and the user outputs are redirected to the respective files.
     * The amount of time taken can be determined by subtracting the startTime from the stopTime. The time's unit, which is in nanoseconds is then converted to milliseconds.
     * The .class file is then deleted.
     * @return p.exitValue() The exit value of the compilation process.
     */
    private int runJava() 
    {
        File dir = new File(Integer.toString(submissionID));
        File[] classfiles = dir.listFiles(new FilenameFilter() 
        {
            @Override
            public boolean accept(File dir, String name) 
            {
                return name.endsWith(".class");
            }
        });
        String classname = classfiles[0].getName().substring(0,classfiles[0].getName().lastIndexOf("."));
        System.out.println(classname);
        
        run = new ProcessBuilder("java",classname).directory( new File(Integer.toString(submissionID)));
        run.redirectInput(inputs);
        run.redirectOutput(userOutputs);

        try {
            long startTime = System.nanoTime();
            Process p = run.start();
            try {
                if (!p.waitFor(Integer.parseInt(problem.getTimeLimit()), TimeUnit.MILLISECONDS)) 
                {
                    long stopTime = System.nanoTime();
                    p.destroy();
                    timeTaken = (stopTime - startTime)/1000000;
                    classfiles[0].delete();
                    return -1;
                }
            } catch (InterruptedException ex) {
                p.destroy();
                classfiles[0].delete();
                return -1;
            }
            long stopTime = System.nanoTime();
            timeTaken = (stopTime - startTime)/1000000;
            classfiles[0].delete();
            return p.exitValue();

        } catch (IOException ex) {
            System.out.println("At runJava Runtime Error: " + ex.getMessage());
            classfiles[0].delete();
            return -2;
        }
    }
    /**
     * This function overrides the run() function of the Runnable class.
     * When an object implementing interface Runnable is used to create a thread, starting the thread causes the object's run method to be called in that separately executing thread.
     */
    @Override
    public void run() 
    {
        System.out.println(submission.getLanguage());
        int IOFileState;

        inputs = new File(folderPath + submissionID + ".in");
        expectedOutputs = new File(folderPath + submissionID + ".out");
        userOutputs = new File(folderPath + "u" + submissionID + ".out");

        try {
            FileOutputStream fosInp = new FileOutputStream(inputs);
            FileOutputStream fosOup = new FileOutputStream(expectedOutputs);
            fosInp.write(problem.getInp());
            fosOup.write(problem.getOutp());
            fosInp.close();
            fosOup.close();
            IOFileState = 0;
        } catch (FileNotFoundException ex) {
            IOFileState = -1;
            System.out.println("At Compiler IO file Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("At Compiler IO file Error: " + ex.getMessage());
            IOFileState = -1;
        }

        if (IOFileState < 0) 
        {
            return;
        }
        boolean ErrBeforeCompareOutput = false;
        switch (submission.getLanguage()) 
        {
            case "C":
                if (compileC() != 0) 
                {
                    db.updateVerdict(submissionID, "Compilation Error",-1);
                    ErrBeforeCompareOutput = true;
                } 
                else 
                {
                    int verdict = runCppC();
                    System.out.println(verdict);
                    if (verdict == -1) 
                    {
                        db.updateVerdict(submissionID, "Time Limit Exceeded",(int)timeTaken);
                        ErrBeforeCompareOutput = true;
                    } 
                    else if (verdict != 0) 
                    {
                        db.updateVerdict(submissionID, "Run Time Error",-1);
                        ErrBeforeCompareOutput = true;
                    }
                }
                break;
            case "C++":
                if (compileCpp() != 0) 
                {
                    db.updateVerdict(submissionID, "Compilation Error",-1);
                    ErrBeforeCompareOutput = true;
                }
                else
                {
                    int verdict = runCppC();
                    System.out.println(verdict);
                    if (verdict == -1) 
                    {
                        db.updateVerdict(submissionID, "Time Limit Exceeded",(int)timeTaken);
                        ErrBeforeCompareOutput = true;
                    } 
                    else if (verdict != 0) 
                    {
                        db.updateVerdict(submissionID, "Run Time Error",-1);
                        ErrBeforeCompareOutput = true;
                    }
                }
                break;
            case "Java":
                if (compileJava() != 0) 
                {
                    db.updateVerdict(submissionID, "Compilation Error",-1);
                    ErrBeforeCompareOutput = true;
                }
                else 
                {
                    int verdict = runJava();
                    if (verdict == -1) 
                    {
                        db.updateVerdict(submissionID, "Time Limit Exceeded",(int)timeTaken);
                        ErrBeforeCompareOutput = true;
                    } 
                    else if (verdict != 0) 
                    {
                        db.updateVerdict(submissionID, "Run Time Error",-1);
                        ErrBeforeCompareOutput = true;
                    }
                }
                break;
            default:
                break;

        }

        if (!ErrBeforeCompareOutput) 
        {
            int comparisonResult;
            compare = new ProcessBuilder("fc", expectedOutputs.getAbsolutePath(), userOutputs.getAbsolutePath());

            try {
                Process p = compare.start();
                p.waitFor(2, TimeUnit.MINUTES);
                comparisonResult = p.exitValue();
                if (comparisonResult == 0) 
                {
                    db.updateVerdict(submissionID, "Accepted",(int)timeTaken);
                } 
                else if (comparisonResult == 1) 
                {
                    db.updateVerdict(submissionID, "Wrong Answer",(int)timeTaken);
                } 
                else 
                {
                    System.out.println("Output File is Missing!");
                }
            } catch (IOException ex) {
                System.out.println("Output Compare Process Error: " + ex.getMessage());
            } catch (InterruptedException ex) {
                System.out.println("Comparing outputs took too long!");
            }
        }
        expectedOutputs.delete();
        inputs.delete();
        userOutputs.delete();
        submittedFile.delete();
        new File(folderPath + submissionID + ".exe").delete();
        new File(Integer.toString(submissionID)).delete();
    }

}
