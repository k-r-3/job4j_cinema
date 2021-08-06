<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>

    <title>Кинотеатр</title>
</head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    function ticketCreate() {
        var forms = document.getElementsByClassName("form-check-input"),
            dot;
        for (var i = 0; i < forms.length; i++) {
            var value = forms.item(i);
            if (value.checked === true) {
                dot = value;
            }
        }
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/cinema/hall',
            data: JSON.stringify({
                name: $(dot).attr('value')
            })
        }).done(function () {
            window.location.href = 'http://localhost:8080/cinema/payment.html'
        }).fail(function (err) {
            console.log(err);
        })
    }
</script>
<body>

<div class="container">
    <div class="row pt-3">
        <h4>
            Бронирование мест на сеанс
        </h4>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 120px;">Ряд / Место</th>
                <th>1</th>
                <th>2</th>
                <th>3</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th>1</th>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="11"
                               checked>
                        Ряд 1, Место 1
                    </div>
                </td>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="12"
                               checked>
                        Ряд 1, Место 2
                    </div>
                </td>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="13"
                               checked>
                        Ряд 1, Место 3
                    </div>
                </td>
            </tr>
            <tr>
                <th>2</th>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="21"
                               checked>
                        Ряд 2, Место 1
                    </div>
                </td>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="22"
                               checked>
                        Ряд 2, Место 2
                    </div>
                </td>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="23"
                               checked>
                        Ряд 2, Место 3
                    </div>
                </td>
            </tr>
            <tr>
                <th>3</th>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="31"
                               checked>
                        Ряд 3, Место 1
                    </div>
                </td>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="32"
                               checked>
                        Ряд 3, Место 2
                    </div>
                </td>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="place" value="33"
                               checked>
                        Ряд 3, Место 3
                    </div>
                </td>
            </tr>
            <div class="row float-right">
                <button type="submit" class="btn btn-success" onclick="ticketCreate()">Оплатить</button>
            </div>
            </tbody>
            <caption>${error}</caption>
        </table>
    </div>
</div>
</body>
</html>