package controllers;

import play.mvc.Call;
import forms.PaginationFilter;
import forms.PartialSorting;
import forms.PartialSorting.SortDirection;
import forms.PartialSorting.SortableAttribute;
import forms.SelectionFilter;

public class ServerSideLogicContext implements Context{

	private final PaginationFilter paginationFilter;

	private final SelectionFilter selectionFilter;

	private final PartialSorting partialSorting;

	private final IssuesOverviewState state;

	public PaginationFilter getPaginationFilter() {
		return paginationFilter;
	}

	public SelectionFilter getSelectionFilter() {
		return selectionFilter;
	}

	public PartialSorting getPartialSorting() {
		return partialSorting;
	}

	public ServerSideLogicContext(PaginationFilter paginationFilter,
			SelectionFilter selectionFilter, PartialSorting partialSorting,
			IssuesOverviewState state) {
		super();
		this.paginationFilter = paginationFilter;
		this.selectionFilter = selectionFilter;
		this.partialSorting = partialSorting;
		this.state = state;
	}

	@Override
	public boolean isStateOpen() {
		return IssuesOverviewState.OPEN.equals(state);
	}

	@Override
	public boolean isStateClosed() {
		return IssuesOverviewState.CLOSED.equals(state);
	}

	@Override
	public boolean isStateAssignedCurrentUser() {
		return IssuesOverviewState.ASSIGNED_CURRENT_USER.equals(state);
	}

	@Override
	public boolean isStateAssignedOtherUser() {
		return IssuesOverviewState.ASSIGNED_OTHERS.equals(state);
	}

	public IssuesOverviewState getState() {
		return state;
	}

	@Override
	public Call getUnassignIssueUri() {
		return routes.IssuesController.unassignIssue(partialSorting,
				paginationFilter, selectionFilter);
	}

	@Override
	public Call getAssignIssueUri() {
		return routes.IssuesController.assignIssueToUser(
				AuthentificationAction.getCurrentUserName(), partialSorting,
				paginationFilter, selectionFilter);
	}

	@Override
	public Call getClosingProcessUri() {
		return routes.IssuesController.issuesClosing();
	}

	public String getAllIssuesWithNewSortingUri(SortableAttribute attribute,
			SortDirection direction) {
		return routes.IssuesController.getAllIssues(partialSorting,
				paginationFilter, selectionFilter,
				IssueOverviewStateBinder.create(state))
				+ "&"
				+ partialSorting.getUrlForTopSorting(attribute, direction);
	}

	public Call getIssuesOverviewUriForPage(int pageNumber) {
		return routes.IssuesController.getAllIssues(partialSorting,
				new forms.PaginationFilter(pageNumber), selectionFilter,
				IssueOverviewStateBinder.create(state));
	}

	@Override
	public boolean isFilterable() {
		return true;
	}

	@Override
	public Call getOverviewUriForState(IssuesOverviewState state) {
		return routes.IssuesController.getAllIssues(new PartialSorting(), new PaginationFilter(), new SelectionFilter(), IssueOverviewStateBinder.create(state));
	}

	@Override
	public Call getUriForIssue(Long id) {
		return routes.IssuesController.getIssue(id);
	}

	@Override
	public Call getCloseIssuesUri() {
		return routes.IssuesController.closeIssues();
	}

	@Override
	public Call getUpdateIssueUri(Long id) {
		return routes.IssuesController.updateIssue(id);
	}
	


	
}
