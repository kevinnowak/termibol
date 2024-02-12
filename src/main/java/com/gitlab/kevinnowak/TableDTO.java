package com.gitlab.kevinnowak;

import java.util.List;

record TableDTO(String code, List<TableRowDTO> tableRows) {
}
