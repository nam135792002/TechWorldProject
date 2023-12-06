$(document).ready(function() {
    $('#menu').metisMenu();

    $('#example').DataTable({
        "lengthMenu": [ [5, 10, 25, -1], [5, 10, 25, "All"] ],
        "pageLength": 5
    });

    $("#logoutLink").on("click",function (e){
        e.preventDefault();
        document.logoutForm.submit();
    });
});