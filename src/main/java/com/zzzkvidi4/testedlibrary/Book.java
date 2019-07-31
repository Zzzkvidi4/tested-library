package com.zzzkvidi4.testedlibrary;

import lombok.*;
import org.jetbrains.annotations.Nullable;

/**
 * Book DTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Book {
    @Nullable
    @Setter(onParam_ = @NonNull)
    private String name;
    @Nullable
    @Setter(onParam_ = @NonNull)
    private Author author;
}
