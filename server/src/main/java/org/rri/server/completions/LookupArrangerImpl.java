package org.rri.server.completions;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResult;
import com.intellij.codeInsight.lookup.Lookup;
import com.intellij.codeInsight.lookup.LookupArranger;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class LookupArrangerImpl extends LookupArranger {
  @NotNull
  private final CompletionParameters parameters;
  @NotNull
  private final ArrayList<LookupElement> items = new ArrayList<>();


  public LookupArrangerImpl(@NotNull CompletionParameters parameters) {
    this.parameters = parameters;
  }

  /* todo
  Add completion results sorting
 */
  public void addElement(@NotNull CompletionResult completionItem) {
    var presentation = new LookupElementPresentation();
    ReadAction.run(() -> completionItem.getLookupElement().renderElement(presentation));

    items.add(completionItem.getLookupElement());
    super.addElement(completionItem.getLookupElement(), presentation);
  }

  @Override
  @NotNull
  public Pair<List<LookupElement>, Integer> arrangeItems(@NotNull Lookup lookup, boolean onExplicitAction) {
    var toSelect = 0;
    return new Pair<>(items, toSelect);
  }

  @Override
  @NotNull
  public LookupArranger createEmptyCopy() {
    return new LookupArrangerImpl(parameters);
  }

}