package bbr.b2b.portal.servlets.publishing.classes;

import org.apache.commons.fileupload.FileItem;

public interface FileSaveManager 
{
    String saveFile(FileItem fileItem, String fullContextPath , String fileBaseDir, String fileDomain, FileType fileType);
}

