let upload_img = document.querySelector("#upload_img");
let get_img = document.querySelector("#get_img");

get_img.onchange = function(){
    upload_img.style.display = "inline"
    upload_img.src = URL.createObjectURL(get_img.files[0])
}