// $(function () {
// 	$('.add-btn').on('click', function () {
// 		var parentTable = $(this).closest('table');
// 		var num = $(this).data('index');
// 		var array = 'forms[' + (num + 1) + ']';
// 		var formControl = 'form-control';
//
// 		var tr = document.createElement('tr');
//
// 		var typeSelect = document.createElement('select');
// 		$(typeSelect).addClass(formControl);
// 		$(typeSelect).attr('name', array + '.type');
// 		$(typeSelect).append('<option value=""></option><option th:each="type : ${types}" th:value="${type}" th:text="#{|longleave.type.${type}|}">産休</option>');
// 		var selectTd = document.createElement('td');
// 		$(selectTd).append(typeSelect);
// 		$(tr).append(selectTd);
//
// 		var startAtInput = document.createElement('input');
// 		$(startAtInput).addClass(formControl);
// 		$(startAtInput).attr('name', array + '.startAt');
// 		var startAtTd = document.createElement('td');
// 		$(startAtTd).append(startAtInput);
// 		$(tr).append(startAtTd);
//
// 		var endAtInput = document.createElement('input');
// 		$(endAtInput).addClass(formControl);
// 		$(endAtInput).attr('name', array + '.endAt');
// 		var endAtTd = document.createElement('td');
// 		$(endAtTd).append(endAtInput);
// 		$(tr).append(endAtTd);
//
// 		var remarksInput = document.createElement('input');
// 		$(remarksInput).addClass(formControl);
// 		$(remarksInput).attr('name', array + '.remarks');
// 		$(remarksInput).attr('placeholder', '備考');
// 		var remarksTd = document.createElement('td');
// 		remarksTd.append(remarksInput);
// 		$(tr).append(remarksTd);
//
// 		$(parentTable).append(tr);
//
// 	})
// });
