package bbr.b2b.portal.servlets.publishing.classes;

import java.io.File;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class DefaultFileSaveManager implements FileSaveManager
{

	@Override
	public String saveFile(FileItem fileItem, String fullContextPath, String fileBaseDir, String fileDomain, FileType fileType)
	{
		String originalFileName = FilenameUtils.getName(fileItem.getName());
		String relUrl;

		String subDir = File.separator + fileType.getValue() + File.separator + DirectoryPathManager.getDirectoryPathByDateType(DirectoryPathManager.DIR_DATE_TYPE.DATE_POLICY_YYYY_MM);
		// FIXME: CAMBIAR RANDOM POR DECODIFICADOR
		String fileName = replaceSpacesWithUnderscore(originalFileName);

		File newFile = new File(fileBaseDir + subDir + fileName);
		File fileToSave = DirectoryPathManager.getUniqueFile(newFile.getAbsoluteFile());

		try
		{
			FileUtils.writeByteArrayToFile(fileToSave, fileItem.get());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		String savedFileName = FilenameUtils.getName(fileToSave.getAbsolutePath());
		relUrl = StringUtils.replace(subDir, "\\", "/") + savedFileName;

		return fullContextPath + fileDomain + relUrl;
	}

	private String replaceSpacesWithUnderscore(String filename)
	{
		String result = StringUtils.replace(filename, " ", "_");
		return result;
	}
}
