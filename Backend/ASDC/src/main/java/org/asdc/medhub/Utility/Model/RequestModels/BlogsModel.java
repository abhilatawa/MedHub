package org.asdc.medhub.Utility.Model.RequestModels;import jakarta.validation.constraints.NotBlank;import lombok.AllArgsConstructor;import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;import java.sql.Timestamp;/** * Model used for creating a new blog post */@Getter@Setter@AllArgsConstructor@NoArgsConstructorpublic class BlogsModel {    /**     * Title of the blog     */    @NotBlank    private String title;    /**     * Description of the blog     */    @NotBlank    private String description;    /**     * Timestamp when the blog was created     */    private Timestamp createdAt;}