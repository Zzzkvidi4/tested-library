package com.zzzkvidi4.testedlibrary;

import lombok.*;
import org.jetbrains.annotations.Nullable;

/**
 * Author DTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Author {
    @Nullable
    @Setter(onParam_ = @NonNull)
    private String name;
}
