package org.openxava.actions;

import java.util.*;

import javax.inject.*;

import org.apache.commons.fileupload.*;
import org.openxava.calculators.*;
import org.openxava.model.*;
import org.openxava.util.*;
import org.openxava.web.editors.*;

/**
 * Logic of UploadFileIntoFileset.uploadFile action in default-controllers.xml. <p>
 * 
 * @author Jeromy Altuna
 */
public class LoadIntoAttachedFilesAction extends    /* tmp ViewBaseAction */ GenerateIdForPropertyBaseAction /* tmp */ 
										 implements /* tmp INavigationAction, */ 
										 			IProcessLoadedFileAction 
{
	
	@SuppressWarnings("rawtypes")
	private List fileItems;
	
	/* tmp
	@Inject
	private String newFilesetProperty;
	*/
	
	private String property;
	
	@Override
	public void execute() throws Exception {
		System.out.println("[UploadFileIntoFilesetAction.execute] property=" + property); // tmp 
		// tmp String libraryId = getPreviousView().getValueString(newFilesetProperty);
		String libraryId = generateIdForProperty(property); // tmp 
		Iterator<?> it = fileItems.iterator();		
		int counter = 0;
		StringBuffer filesNames = new StringBuffer(); 
		while(it.hasNext()) {
			FileItem fi = (FileItem) it.next();
			if(!Is.emptyString(fi.getName())) {
				AttachedFile file = new AttachedFile();
				file.setName(fi.getName());
				file.setLibraryId(libraryId);
				file.setData(fi.get());
				FilePersistorFactory.getInstance().save(file);
				counter++;
				if (filesNames.length() > 0) filesNames.append(", ");
				filesNames.append(fi.getName());
			}
		}
		/* tmp
		if(counter == 1) addMessage("file_added_to_fileset", newFilesetProperty);
		else if(counter > 1) addMessage("files_added_to_fileset", 
										newFilesetProperty, counter);
		*/
		// tmp ini
		if(counter == 1) addMessage("file_added_to_fileset", property);
		else if(counter > 1) addMessage("files_added_to_fileset", property, counter);		
		// tmp fin
		
		trackModification(filesNames.toString()); 
		// tmp closeDialog();
	}
	
	private void trackModification(String fileName) {  
		Map oldChangedValues = new HashMap();
		// tmp oldChangedValues.put(newFilesetProperty, XavaResources.getString("files_files_added"));  
		oldChangedValues.put(property, XavaResources.getString("files_files_added")); // tmp
		Map newChangedValues = new HashMap();
		// tmp newChangedValues.put(newFilesetProperty, fileName); 
		newChangedValues.put(property, fileName); // tmp
		// tmp AccessTracker.modified(getPreviousView().getModelName(), getPreviousView().getKeyValues(), oldChangedValues, newChangedValues);
		AccessTracker.modified(getView().getModelName(), getView().getKeyValues(), oldChangedValues, newChangedValues); // tmp
	}
	
	/* tmp
	@Override
	public String[] getNextControllers() throws Exception {
		return PREVIOUS_CONTROLLERS;
	}	
	
	@Override
	public String getCustomView() throws Exception {
		return PREVIOUS_VIEW;
	}
	*/

	@Override @SuppressWarnings("rawtypes")
	public void setFileItems(List fileItems) {
		this.fileItems = fileItems;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
