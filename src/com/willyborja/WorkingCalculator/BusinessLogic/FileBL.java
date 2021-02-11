package com.willyborja.WorkingCalculator.BusinessLogic;

import com.willyborja.WorkingCalculator.DAO.FileReaderDAO;

import java.util.List;

public class FileBL {

    private FileReaderDAO fileReaderDAO = new FileReaderDAO();

    public List<String> readCommand(){
        fileReaderDAO.openFile();
        return fileReaderDAO.readFile();
    }

    public void closeFile(){
        fileReaderDAO.closeFile();
    }



}
