
//id btn-save 클릭 이벤트시 세이브 함수실행
let index = {
	init: function() {
		$("#btn-save").on("click", () => {//function(){},()=>{} this를 바인딩하기 위해서
			this.save();
		});
	},

	save: function() {
		//alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};


		//console.log(data);

		//ajax호출시 default가 비동기 호출
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
		$.ajax({
			//회원가입 수행 요청(100초 가정)
			type: "POST",
			url: "/blog/api/user",
			data: JSON.stringify(data),//http body데이터, data객체를 JSON 문자열 형식으로 변환
			contentType: "application/json; charset=utf-8",//body데이터가 어떤타임인지(MIME)
			dataType: "json"//요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp) {
			//정상시 done함수 실행
			alert("회원가입이 완료되었습니다");
			console.log(resp);
			//location.href="/blog";
		}).fail(function(error) {
			//실패시 fail함수 실행
			alert(JSON.stringify(error));
		}); //ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
	}
}

index.init();