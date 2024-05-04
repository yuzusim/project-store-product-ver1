$(document).ready(function () {

    //이미지파일 올리기
    $('#imageUpload').change(function (event) {
        if (this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#profilePreview').html('<img src="' + e.target.result + '" style="max-width: 100%;">');
            };
            reader.readAsDataURL(this.files[0]);
            let fileName = this.files[0].name; // 파일 이름 가져오기
            console.log(fileName); // 콘솔에 파일 이름 출력
        }
    });

    // 수량 증가 버튼 클릭 이벤트
    $(".increase-btn").click(function () {
        var $quantityInput = $(this).siblings(".quantity");
        var currentQuantity = parseInt($quantityInput.val());
        $quantityInput.val(currentQuantity + 1);
    });

    // 수량 감소 버튼 클릭 이벤트
    $(".decrease-btn").click(function () {
        var $quantityInput = $(this).siblings(".quantity");
        var currentQuantity = parseInt($quantityInput.val());
        if (currentQuantity > 1) {
            $quantityInput.val(currentQuantity - 1);
        }
    });




});
