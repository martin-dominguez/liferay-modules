<%@ include file="/init.jsp" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
  google.charts.load('current', {packages:["orgchart"]});
  google.charts.setOnLoadCallback(drawChart);

  function drawChart() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Name');
    data.addColumn('string', 'Manager');
    data.addColumn('string', 'ToolTip');

    // For each orgchart box, provide the name, manager, and tooltip to show.
    data.addRows([
	    <c:forEach items="${users}" var="user">
		    <c:choose>
			    <c:when test="${user.status eq 0 && not empty user.firstName && not empty user.jobTitle}">
					<c:set var="userId">${ user.userId }</c:set>
					<c:set var="fullName">${ user.firstName } ${ user.lastName }</c:set>
					<fmt:setTimeZone value="${ user.timeZoneId }" />
					<fmt:formatDate type="time" timeStyle="short" value="<%=new java.util.Date()%>" var="time" />
					<c:set var="following"></c:set>
					<%
						User _user = (User)pageContext.getAttribute("user");
						boolean following = SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), _user.getUserId(), SocialRelationConstants.TYPE_UNI_FOLLOWER);
						int followerUsersCount = SocialRelationLocalServiceUtil.getInverseRelationsCount(_user.getUserId(), SocialRelationConstants.TYPE_UNI_FOLLOWER);
					%>
				    [{
				    	'v': '${user.screenName}',
				    	'f': '<a data-name="${fullname}" data-user="${userId}" data-fullname="${fullName}" data-mail="${user.emailAddress}" data-title="${user.jobTitle}" \
				    	data-time="${time}" data-lastc="${user.loginDate}" data-following="<%=following%>" data-followers="<%=followerUsersCount%>" \
				    	data-toggle="modal" data-target="#profile-modal"> \
				    	<liferay-ui:user-portrait userId="${userId}" size="xl" /></a> \
				    	<div class="user-data">${fullName} \
				    	<br/><span class="job-title">${user.jobTitle}</span></div>'
				    },
				    '${managers[userId]}',
				    '${fullName} - ${user.jobTitle}'],
			    </c:when>
		    </c:choose>
		    
		</c:forEach>
	]);

    // Create the chart.
    var chart = new google.visualization.OrgChart(document.getElementById('chart_div'));
    // Draw the chart, setting the allowHtml option to true for the tooltips.
    var options = {
    		'allowHtml':true,
    		'allowCollapse':true,
    		'nodeClass':'org-node',
    		'selectedNodeClass':'selected-org-node'
    }
    chart.draw(data, options);
  }
</script>
<div>${data}</div>
<div id="chart_div"></div>
<div 
 	class="modal fade" 
 	style="display:none"
 	id="profile-modal"
 	tabindex="-1" 
 	role="dialog" 
 	aria-labelledby="profile-modal"
 	aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="title-holder d-block text-center">
                    	<div class="ds-avatar"></div> 
                        <h5 class="modal-title" id="profileModalTitle">Dummy Text</h5>
    					<h6 title="Followers">
    						<liferay-ui:icon icon="users" markupView="lexicon" message="followers" />
    						<span class="followers">90</span>
    					</h6>
                    </div>
					<button
						aria-labelledby="Close"
						class="close"
						data-dismiss="modal"
						role="button"
						type="button"
					>
						<liferay-ui:icon icon="times" markupView="lexicon" message="close" />
					</button>
                </div>
                <div class="modal-body">
                	<c:if test="${ themeDisplay.isSignedIn()}"> 
	                    <div class="button text-center">
	                        <a href="#" onclick="$('#profile-modal').modal('hide')" class="follow btn btn-outline-primary">
	                        	<liferay-ui:message key="follow" /> 
	                        </a>
	                    </div>
	                </c:if>
                    <div class="ds-email ds-attr">
                        <h6><liferay-ui:icon icon="envelope-closed" markupView="lexicon" message="email" /> <liferay-ui:message key="email" />
                            <a href="" class="ds-text ds-email-text"><liferay-ui:message key="email" /></a>
                        </h6>
                    </div>
                    <div class="ds-title ds-attr">
                        <h6><liferay-ui:icon icon="myspace" markupView="lexicon" message="job title" /> <liferay-ui:message key="job-title" />
                            <span class="ds-text ds-title-text"><liferay-ui:message key="job-title" /></span>
                        </h6>
                    </div>
                    <div class="ds-time ds-attr">
                        <h6><liferay-ui:icon icon="time" markupView="lexicon" message="local time" /> <liferay-ui:message key="local-time" />
                            <span class="ds-text ds-time-text"><liferay-ui:message key="local-time" /></span>
                        </h6>
                    </div>
                    <div class="ds-lastc ds-attr">
                        <h6><liferay-ui:icon icon="check-circle" markupView="lexicon" message="last connection" /> <liferay-ui:message key="last-connection" />
                            <span class="ds-text ds-lastc-text"><liferay-ui:message key="last-connection" /></span>
                        </h6>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script>
	$('#profile-modal').on('show.bs.modal', function (event) {
	    var name = $(event.relatedTarget).data('fullname');
	    var mail = $(event.relatedTarget).data('mail');
	    var title = $(event.relatedTarget).data('title');
	    var lastc = $(event.relatedTarget).data('lastc');
	    var time = $(event.relatedTarget).data('time');
	    var user = $(event.relatedTarget).data('user');
	    var following = $(event.relatedTarget).data('following');
	    var followers = $(event.relatedTarget).data('followers');
	    $(this).find(".modal-title").text(name);
	    $(this).find(".followers").text(followers);
	    $(this).find(".ds-title-text").text(title);
	    $(this).find(".ds-email-text").text(mail);
	    $(this).find(".ds-email-text").attr("href", "mailto:" + mail);
	    $(this).find(".ds-time-text").text(time);
	    $(this).find(".ds-lastc-text").text(lastc);
	    $(this).find(".ds-avatar").html($(event.relatedTarget).find(".sticker").clone());
	    
	    var follow = $(this).find(".follow");
	    if (user !== ${themeDisplay.getUserId()}) {
			follow.removeClass("d-none");
			if (following) {
		   		follow.text('<%= UnicodeLanguageUtil.get(request, "unfollow") %>');
		   		follow.removeClass("btn-outline-primary");
	   	    	follow.addClass("btn-primary");
	   	    	$(this).find(".follow .lexicon-icon").toggleClass("lexicon-icon-plus lexicon-icon-times");
	   	    	$(this).find(".follow .lexicon-icon use").attr("href", "/o/classic-theme/images/clay/icons.svg#times")
	   	    } else {
	   	    	follow.text('<%= UnicodeLanguageUtil.get(request, "follow") %>');
	   	    	follow.removeClass("btn-primary");
	   	    	follow.addClass("btn-outline-primary");
	   	    	$(this).find(".follow .lexicon-icon").toggleClass("lexicon-icon-times lexicon-icon-plus");
	   	    	$(this).find(".follow .lexicon-icon use").attr("href", "/o/classic-theme/images/clay/icons.svg#plus")
	   	    }
	    } else {
	    	follow.addClass("d-none");
	    }
	    
   
	    
	    var basePortletURL = Liferay.ThemeDisplay.getPortalURL() + Liferay.currentURL;
	    var actionURL = Liferay.Util.PortletURL.createActionURL(
	    		basePortletURL,
	    		  {
	    			'p_p_id': "<%= themeDisplay.getPortletDisplay().getId() %>",
	    		    'javax.portlet.action': 'follow',
					'p_p_state': "<%=LiferayWindowState.NORMAL.toString() %>",
					'p_p_mode': "<%=LiferayPortletMode.VIEW %>",
	    		    'user': user,
	    		    'action': following ? "unfollow" : "follow",
	    		    'p_auth': Liferay.authToken
	    		  }  
	    		);
		follow.attr('href', actionURL.toString());
	});
	$('#profile-modal').on('hidden.bs.modal', function () {
		$('.selected-org-node').removeClass('selected-org-node');
	});
</script>
