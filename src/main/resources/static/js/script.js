(function ($) {

    "use strict";





    $(document).ready(function () {

        // product single page
        var thumb_slider = new Swiper(".product-thumbnail-slider", {
            autoplay: true,
            loop: true,
            spaceBetween: 8,
            slidesPerView: 4,
            freeMode: true,
            watchSlidesProgress: true,
        });

        var large_slider = new Swiper(".product-large-slider", {
            autoplay: true,
            loop: true,
            spaceBetween: 10,
            effect: 'fade',
            thumbs: {
                swiper: thumb_slider,
            },
        });


        // rental swiper
        var swiper = new Swiper(".rental-swiper", {
            slidesPerView: 3,
            spaceBetween: 30,
            freeMode: true,
            navigation: {
                nextEl: ".rental-swiper-next",
                prevEl: ".rental-swiper-prev",
            },
            breakpoints: {
                0: {
                    slidesPerView: 1,
                    spaceBetween: 20,
                },
                768: {
                    slidesPerView: 2,
                    spaceBetween: 30,
                },
                1400: {
                    slidesPerView: 3,
                    spaceBetween: 30,
                },
            }
        });


        //testimonial swiper
        var swiper = new Swiper(".testimonial-swiper", {
            loop: true,
            pagination: {
                el: ".swiper-pagination",
            },
        });




        //date picker
        $("#datepicker1, #datepicker2").datepicker({
            autoclose: true,
            todayHighlight: true,
        }).datepicker('update', new Date());



        // Animate on Scroll
        AOS.init({
            duration: 1000,
            once: true,
        })




    });


})(jQuery);
