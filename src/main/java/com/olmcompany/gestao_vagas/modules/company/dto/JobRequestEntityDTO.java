package com.olmcompany.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

public class JobRequestEntityDTO {

    @Schema(example = "Vaga JAVA para desenvolvedor júnior!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "GYMpass, DayOff, Plano de Saúde", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Vaga JAVA para desenvolvedor júnior!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}
