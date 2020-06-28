$(document).ready(function(){

  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const boardId = urlParams.get('board_id')

  var boardResp = function () {
    var tmp = null;
    $.ajax({
      'async': false,
      'type': "GET",
      'global': false,
      'dataType': 'json',
      'url': "http://localhost:5000/minesweeper-api/boards/" + boardId,
      'success': function (data) {
        tmp = data;
      }
    });
    return tmp;
  }();

  draw_board(boardResp);


  $('#draw-board').on('click', 'table td', function (){
    var tileRow = $(this).attr("tileRow");
    var tileCol = $(this).attr("tileCol");

    var boardRevealedResp = function () {
      var tmp = null;
      $.ajax({
        'async': false,
        'type': "PUT",
        'global': false,
        'dataType': 'json',
        'url': "http://localhost:5000/minesweeper-api/boards/" + boardId + "/reveal?tile_row=" + tileRow + "&tile_col=" + tileCol,
        'success': function (data) {
          tmp = data;
        }
      });
      return tmp;
    }();

    draw_board(boardRevealedResp);

  });

});



function draw_board(boardResp) {
  $("#draw-board").empty();

  var board = boardResp;
  var tiles = board.tiles;
  var rows = board.rows;
  var cols = board.cols;

  var draw = "";

  draw += '<h3 class="h4 mb-3 font-weight-normal">Status: ';
  switch(board.status) {
    case "WON":
      draw += '<span class="green">'; break;
    case "LOST":
      draw += '<span class="red">'; break;
    default:
      draw += '<span class="blue">'; break;
  }
  draw += board.status;
  draw += '</span></h3>'

  draw += '<table class="table table-bordered">';
  for (x = 0; x < rows; x++) {
    draw+= '<tr>';
    for (y = 0; y < cols; y++) {

      var tileShow = tiles.find(function (tile) {
        return tile.row == x && tile.col == y;
      });

      if (tileShow.mine && tileShow.revealed) {
        draw+= '<td tileRow="'+tileShow.row+'" tileCol="'+tileShow.col+'" class="mine">✱</td>';
      } else if (tileShow.revealed) {

        switch(tileShow.neighbor_mine_count) {
          case 0:
            draw+= '<td tileRow="'+tileShow.row+'" tileCol="'+tileShow.col+'" class="revealed">'; break;
          case 1:
            draw+= '<td tileRow="'+tileShow.row+'" tileCol="'+tileShow.col+'" class="revealed blue">1'; break;
          case 2:
            draw+= '<td tileRow="'+tileShow.row+'" tileCol="'+tileShow.col+'" class="revealed green">2'; break;
          case 3:
            draw+= '<td tileRow="'+tileShow.row+'" tileCol="'+tileShow.col+'" class="revealed red">3'; break;
          default:
            draw+= '<td tileRow="'+tileShow.row+'" tileCol="'+tileShow.col+'" class="revealed black">';
            draw+= tileShow.neighbor_mine_count;
        }
        draw+= '</td>';

      } else {
        draw+= '<td tileRow="'+tileShow.row+'" tileCol="'+tileShow.col+'"></td>';
      }

    }
    draw+= '</tr>';
  }
  draw+= '</table>';

  if (board.status != "PLAYING") {
    draw+= '<br>';
    draw+= '<a href="http://localhost:5000/minesweeper-api/demo/" style="max-width: 200px; margin: 0 auto" class="btn btn-lg btn-primary btn-block">Play Again!</a>';
  }


  $("#draw-board").append(draw);
}