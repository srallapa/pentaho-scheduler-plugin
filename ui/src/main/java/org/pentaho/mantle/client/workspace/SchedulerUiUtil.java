/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2024 Hitachi Vantara. All rights reserved.
 */
package org.pentaho.mantle.client.workspace;

import com.google.gwt.core.client.JsArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SchedulerUiUtil {

  public static final List<String> INJECTED_JOB_PARAM_NAMES = Arrays.asList(
    "::session",
    "accepted-page",
    "ActionAdapterQuartzJob-ActionId",
    "ActionAdapterQuartzJob-ActionUser",
    "ActionAdapterQuartzJob-StreamProvider-InputFile",
    "ActionAdapterQuartzJob-StreamProvider",
    "autoCreateUniqueFilename",
    "autoSubmit",
    "autoSubmitUI",
    "clearLog",
    "directory",
    "gatheringMetrics",
    "htmlProportionalWidth",
    "job",
    "jobName",
    "lineage-id",
    "logLevel",
    "maximum-query-limit",
    "query-limit-ui-enabled",
    "query-limit",
    "renderMode",
    "repositoryName",
    "runSafeMode",
    "scheduleRecurrence",
    "scheduleType",
    "showParameters",
    "timezone",
    "transformation",
    "uiPassParam",
    "user_locale",
    "versionRequestFlags"
  );

  /*
  Filter out the values we inject into scheduled job params for internal scheduler use,
  so that only the user-supplied are returned. The object we get back from the API also
  includes duplicated values, so we remove those too. Also converts the params array into
  a more convenient list type.
   */
  public static ArrayList<JsJobParam> getFilteredJobParams( JsJob job ) {
    JsArray<JsJobParam> paramsRaw = job.getJobParams();
    ArrayList<JsJobParam> params = new ArrayList<>();

    // JsArrays aren't Collections and equals() is a little different for the JavaScriptObjects
    // so we'll iterate, filter(), and distinct() the old-fashioned way...
    HashSet<String> toFilter = new HashSet<>( INJECTED_JOB_PARAM_NAMES );
    HashSet<String> visitedParamNames = new HashSet<>();
    for ( int i = 0; i < paramsRaw.length(); i++ ) {
      JsJobParam param = paramsRaw.get( i );
      if ( !toFilter.contains( param.getName() ) && !visitedParamNames.contains( param.getName() ) ) {
        params.add( paramsRaw.get( i ) );
        visitedParamNames.add( param.getName() );
      }
    }

    return params;
  }
}
