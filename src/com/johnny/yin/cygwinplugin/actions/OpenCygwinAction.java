package com.johnny.yin.cygwinplugin.actions;

import java.io.IOException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.johnny.yin.cygwinplugin.model.Resource;
import com.johnny.yin.cygwinplugin.util.ResourceUtils;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class OpenCygwinAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	private Resource[] resource = null;
	private IStructuredSelection currentSelection;

	/**
	 * The constructor.
	 */
	public OpenCygwinAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		if (!isEnabled()) {
			// MessageDialog.openInformation(new Shell(), "Easy Shell",
			// "Wrong Selection");
			return;
		}

		for (int i = 0; i < resource.length; i++) {
			if (resource[i] == null)
				continue;
			String drive = null;
			String full_path = null;
			String parent_path = null;
			String file_name = null;
			String open_path = null;

			full_path = resource[i].getFile().toString();
			if (resource[i].getFile().isDirectory()) {
				parent_path = resource[i].getFile().getPath();
				file_name = "dir"; // dummy cmd
				open_path = full_path;
			} else {
				parent_path = resource[i].getFile().getParent();
				file_name = resource[i].getFile().getName();
				open_path = parent_path;
			}

			if (full_path != null) {
				// Try to extract drive on Win32
				if (full_path.indexOf(":") != -1) {
					drive = full_path.substring(0, full_path.indexOf(":"));
				}

				try {
					Runtime.getRuntime().exec(
							String.format(
									"cmd.exe /C start %s /D %s mintty.exe",
									"\"" + file_name + "\"", open_path));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				MessageDialog.openInformation(new Shell(), "Open Cygwin",
						"Unable to open Cygwin");
				return;

			}
		}

	}

	public boolean isEnabled() {
		boolean enabled = false;
		if (currentSelection != null) {
			Object[] selectedObjects = currentSelection.toArray();
			if (selectedObjects.length >= 1) {
				resource = new Resource[selectedObjects.length];
				for (int i = 0; i < selectedObjects.length; i++) {
					resource[i] = ResourceUtils.getResource(selectedObjects[i]);
					if (resource[i] != null)
						enabled = true;
				}
			}
		} else {
			// handle Editor selection
			Resource temp = ResourceUtils.getResource(window.getActivePage().getActiveEditor());
			if (temp != null) {
				resource = new Resource[1];
				resource[0] = temp;
				enabled = true;
			}
		}
		return enabled;
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		currentSelection = selection instanceof IStructuredSelection ? (IStructuredSelection) selection
				: null;
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}