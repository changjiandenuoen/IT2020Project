<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

		<link rel="stylesheet" type="text/css" href="/assets/css/general.css">
    	<link rel="stylesheet" type="text/css" href="/assets/css/history.css">
    	
    	<script src="/assets/scripts/functions.js"></script>

	</head>

    <body id="historyPage" onload="initalize()"> <!-- Call the initalize method when the page loads -->

		<script type="text/javascript">
		
			// Method that is called on page load
			function initalize() {
			
				addHTML("/assets/html/statistics.html", "#historyPage");
					
			}
			

		</script>
	
	</body>
	
</html>