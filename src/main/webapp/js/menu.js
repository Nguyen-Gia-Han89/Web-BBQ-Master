/**
 * 
 */
$(document).ready(function() {
    $('.filter-btn').click(function() {
        $('.filter-btn').removeClass('active');
        $(this).addClass('active');
        var filter = $(this).data('filter');
        if (filter === 'all') {
            $('.item-card').fadeIn(300);
        } else {
            $('.item-card').hide();
            $('.item-card[data-category="' + filter + '"]').fadeIn(300);
        }
    });
});